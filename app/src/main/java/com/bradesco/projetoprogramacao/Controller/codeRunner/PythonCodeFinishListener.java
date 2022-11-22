package com.bradesco.projetoprogramacao.controller.codeRunner;

public interface PythonCodeFinishListener {
    /**
     * Will only be called once the executed code finish it naturally,
     * not when its interrupted
     */
    void onFinish(String fullOutput);
}
