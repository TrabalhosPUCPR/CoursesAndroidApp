import threading
import sys
import ctypes
import inspect


def _async_raise(tid, exctype):
    tid = ctypes.c_long(tid)
    if not inspect.isclass(exctype):
        exctype = type(exctype)
    res = ctypes.pythonapi.PyThreadState_SetAsyncExc(tid, ctypes.py_object(exctype))
    if res == 0:
        raise ValueError("Invalid thread id")
    elif res != 1:
        ctypes.pythonapi.PyThreadState_SetAsyncExc(tid, None)
        raise SystemError("Timeout Exception")


def stop_thread(thread):
    _async_raise(thread.ident, SystemExit)


class InterruptableThread(threading.Thread):
    def __init__(self, code):
        super(InterruptableThread, self).__init__(target=text_thread_run, args=(code,), daemon=True)
        self._stop_event = threading.Event()

    def stop(self):
        self._stop_event.set()


class Logger:
    stdout = sys.stdout
    messages = []
    newmessage = False

    def stop(self):
        sys.stdout = self.stdout

    def start(self):
        sys.stdout = self

    def clearlog(self):
        self.messages.clear()
        self.newmessage = False

    def write(self, text):
        if text == "\n":
            self.messages[len(self.messages)-1] += "\n"
        else:
            self.messages.append(text)
            self.newmessage = True

    def flush(self):
        pass


def text_thread_run(code):
    try:
        env = {}
        exec(code, env, env)
    except Exception as e:
        print(e)


def maintextcode(code):
    global runner_thread, log
    log = Logger()
    runner_thread = InterruptableThread(code)
    log.start()
    runner_thread.start()


def isrunning():
    global runner_thread
    return 'runner_thread' in globals() and runner_thread.is_alive()


def consolestring():
    global log
    if not log.newmessage:
        return
    messages = log.messages.copy()
    mes = ""
    for s in messages:
        mes += s
    log.clearlog()
    return mes


def clearlogs():
    global log
    if 'log' in globals():
        log.clearlog()

def stopcode():
    global runner_thread, log
    stop_thread(runner_thread)
    log.stop()

