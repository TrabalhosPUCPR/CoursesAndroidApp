import threading
import sys


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
    global thread1, log
    log = Logger()
    thread1 = InterruptableThread(code)
    log.start()
    thread1.start()
    while (isrunning()):
        ...


def isrunning():
    global thread1
    return thread1.is_alive()


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


def stopcode():
    global thread1, log
    if not isrunning():
        return
    thread1.stop()
    log.stop()