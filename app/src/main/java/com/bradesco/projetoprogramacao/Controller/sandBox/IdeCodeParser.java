package com.bradesco.projetoprogramacao.controller.sandBox;

import android.content.Context;
import android.util.AttributeSet;

import com.google.android.material.textfield.TextInputEditText;

public class IdeCodeParser extends TextInputEditText {
    private Character lastNonEmptyChar = ' ';
    private StringBuilder lastWord = new StringBuilder();
    private int innerFunctions = 0;
    private boolean editingCode = false;
    private final int tabSize = 8;

    public IdeCodeParser(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setText("");
    }

    private void appendCharacter(char c){
        String code = toString();
        if(c == '\n' ){
            if(lastNonEmptyChar == ':'){
                innerFunctions++;
                addToCode(repeatString(" ", innerFunctions*tabSize));
            }
        }else if(Character.getNumericValue(c) == 8){
            if(code.charAt(code.length()-1) == '\n'){
                innerFunctions--;
            }
        }
        lastWord.append(c);
        if(!Character.isSpaceChar(c)) {
            lastWord = new StringBuilder();
            lastNonEmptyChar = c;
        }
    }

    private void addToCode(String s){
        String text = this.toString() + s;
        editingCode = true;
        setText(text);
        editingCode = false;
        setSelection(text.length());
    }

    @Override
    public String toString() {
        if(getText() == null){
            return "";
        }
        return getText().toString();
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        while (!editingCode && start < text.length()){
            appendCharacter(text.charAt(text.length()-1));
            start++;
        }
    }
    private static String repeatString(String s, int count){
        StringBuilder builder = new StringBuilder();
        while(count-- > 0) {
            builder.append(s);
        }
        return builder.toString();
    }
}
