/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 

package components;

/*
 * TextAreaDemo.java requires no other files.
 */

import javax.swing.*;
import java.util.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import javax.swing.text.BadLocationException;


public class TextAreaDemo extends JTextArea
        implements DocumentListener {
    
	private static final long serialVersionUID = -1657455858319197342L;

    private static final String COMMIT_ACTION = "commit";
    private static enum Mode { INSERT, COMPLETION, DONE_COMPLETION };
    private final List<String> words;
    private Mode mode = Mode.INSERT;
    
    
    public TextAreaDemo(List<String> wordsToAdd) {
        this.getDocument().addDocumentListener(this);
        this.getDocument().putProperty("filterNewlines", Boolean.TRUE);
        
        InputMap im = this.getInputMap();
        ActionMap am = this.getActionMap();
        im.put(KeyStroke.getKeyStroke("ENTER"), COMMIT_ACTION);
        am.put(COMMIT_ACTION, new CommitAction());
        
        words = new ArrayList<String>();
        for(String word: wordsToAdd) {
        	words.add(word.toLowerCase());
        }
    }
    
    public void changedUpdate(DocumentEvent ev) {
    }
    
    public void removeUpdate(DocumentEvent ev) {
    }
    
    public void insertUpdate(DocumentEvent ev) {
    	
        if (ev.getLength() != 1) {
            return;
        }
        
        int pos = ev.getOffset();
        String content = null;
        try {
            content = this.getText(0, pos + 1);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        
        // Find where the word starts
        int w;
        for (w = pos; w >= 0; w--) {
            if (! Character.isLetter(content.charAt(w))) {
                break;
            }
        }
        if (pos - w < 2) {
            // Too few chars
            return;
        }
        
        String prefix = content.substring(w + 1).toLowerCase();
        int n = Collections.binarySearch(words, prefix);
        if (n < 0 && -n <= words.size()) {
            String match = words.get(-n - 1);
            if (match.startsWith(prefix)) {
                // A completion is found
                String completion = match.substring(pos - w);
                // We cannot modify Document from within notification,
                // so we submit a task that does the change later
                SwingUtilities.invokeLater(
                        new CompletionTask(completion, pos + 1));
            }
        } else {
            // Nothing found
        	System.out.println("nothing found");
            mode = Mode.INSERT;
        }
    }
    
    private class CompletionTask implements Runnable {
        String completion;
        int position;
        
        CompletionTask(String completion, int position) {
            this.completion = completion;
            this.position = position;
        }
        
        public void run() {
        	System.out.println("1");
        	TextAreaDemo.this.insert(completion, position);
        	TextAreaDemo.this.setCaretPosition(position + completion.length());
        	TextAreaDemo.this.moveCaretPosition(position);
            mode = Mode.COMPLETION;
            System.out.println("2");
        }
    }
    
    private class CommitAction extends AbstractAction {
        public void actionPerformed(ActionEvent ev) {
        	if (mode == Mode.DONE_COMPLETION);
            if (mode == Mode.COMPLETION) {
            	
                int pos = TextAreaDemo.this.getSelectionEnd();
                TextAreaDemo.this.insert(" ", pos);
                TextAreaDemo.this.setCaretPosition(pos);
                mode = Mode.DONE_COMPLETION;
                
            } else {
            	TextAreaDemo.this.replaceSelection("");
            }
        }
    }
   
    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
                UIManager.put("swing.boldMetal", Boolean.FALSE);
                new TextAreaDemoOracle().setVisible(true);
            }
        });
    }
    
    
}
