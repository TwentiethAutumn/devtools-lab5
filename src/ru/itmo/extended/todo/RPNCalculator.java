package ru.itmo.extended.todo;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorAction;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;
import com.intellij.openapi.editor.actionSystem.EditorWriteActionHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RPNCalculator extends EditorAction {

    public RPNCalculator(EditorActionHandler defaultHandler) {
        super(defaultHandler);
    }

    public RPNCalculator() {
        this(new UpHandler());
    }

    private static class UpHandler extends EditorWriteActionHandler {
        private UpHandler() {
        }

        @Override
        public void doExecute(@NotNull Editor editor, @Nullable Caret caret, DataContext dataContext) {
            Document document = editor.getDocument();
            if(!document.isWritable()) return;

            var currentCaret = editor.getCaretModel().getCurrentCaret();
            String variable = currentCaret.getSelectedText();

            try{
                var result = RPN.calculate(variable);
                document.replaceString(currentCaret.getSelectionStart(), currentCaret.getSelectionEnd(), String.valueOf(result));
            }catch(Exception ignored){
            }
        }
    }
}
