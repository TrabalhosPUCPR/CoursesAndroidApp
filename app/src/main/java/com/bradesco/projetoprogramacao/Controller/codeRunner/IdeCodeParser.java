package com.bradesco.projetoprogramacao.controller.codeRunner;

import android.content.Context;
import android.util.AttributeSet;

import com.google.android.material.textfield.TextInputEditText;

import java.util.LinkedList;

public class IdeCodeParser extends TextInputEditText {
    private boolean editingCode = false;
    private final int tabSize = 8;
    LinkedList<Integer> innerFunctions = new LinkedList<>();

    public IdeCodeParser(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setText("");
        innerFunctions.add(0);
    }

    private void appendCharacter(char c){
        String code = toString();
        int cursorPosition = getSelectionStart() - 1;
        int line = getCurrentLine(cursorPosition);
        if(c == '\n' ){
            innerFunctions.add(line, innerFunctions.get(line));
            if(code.length() > 0 && code.charAt(getIndexOfFirstCharInLineBefore(code, cursorPosition - 1)) == ':'){
                innerFunctions.set(line+1, innerFunctions.get(line+1)+1);
            }
            addToCode(repeatString(" ", innerFunctions.get(innerFunctions.size()-1)*tabSize));
        }else if(c == '\b'){
            int pos = getIndexOfFirstCharInLineBefore(code, cursorPosition);
            int col = getCurrentColumn(cursorPosition) + 1;
            String sub;
            if(code.charAt(pos) == '\n'){
                sub = code.substring(0, code.length() - tabSize);
                if(col == tabSize-1){
                    innerFunctions.set(line, innerFunctions.get(line)-1);
                }
                setCode(sub);
            }
        }
    }

    public int getCurrentLine(int cursorPos){
        return getLayout().getLineForOffset(cursorPos);
    }

    public int getCurrentColumn(int cursorPos){
        return cursorPos - getLayout().getLineStart(getCurrentLine(cursorPos));
    }

    private void addToCode(String s){
        String text = this + s;
        setCode(text);
    }

    private void setCode(String s){
        editingCode = true;
        setText(s);
        editingCode = false;
        setSelection(s.length());
    }

    @Override
    public String toString() {
        if(getText() == null){
            return "";
        }
        return getText().toString();
    }

    private int countEmptySpacesBeforeLn(String text, int index){
        int counter = 0;
        while(text.charAt(index--) == ' '){
            counter++;
        }
        return counter;
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        // TODO: 11/20/2022  
        /*
        while (!editingCode && lengthBefore > lengthAfter){
            appendCharacter((char) 8);
            lengthBefore--;
        }
        while (!editingCode && start < text.length()){
            appendCharacter(text.charAt(text.length()-1));
            start++;
        }
        
         */
    }
    private static String repeatString(String s, int count){
        StringBuilder builder = new StringBuilder();
        while(count-- > 0) {
            builder.append(s);
        }
        return builder.toString();
    }
    private static int getIndexOfFirstCharInLineBefore(String text, int index){
        while (text.charAt(index) == ' '){
            index--;
        }
        return index;
    }
}
