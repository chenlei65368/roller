/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  The ASF licenses this file to You
 * under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.  For additional information regarding
 * copyright in this work, please see the NOTICE file in the top level
 * directory of this distribution.
 */

package org.apache.roller.weblogger.ui.rendering.plugins.comments;

import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.roller.weblogger.util.I18nMessages;


/**
 * Asks the commenter to answer a simple math question.
 */
public class MathCommentAuthenticator implements CommentAuthenticator {
    
    private static Log mLogger = LogFactory.getLog(MathCommentAuthenticator.class);
    
    
    public String getHtml(HttpServletRequest request) {
        
        String answer = "";
        
        HttpSession session = request.getSession(true);
        if (session.getAttribute("mathAnswer") == null) {
            // starting a new test
            Random ran = new Random();
            int value1 = ran.nextInt(10);
            int value2 = ran.nextInt(100);
            int sum = value1 + value2;
            session.setAttribute("mathValue1", value1);
            session.setAttribute("mathValue2", value2);
            session.setAttribute("mathAnswer", sum);
        } else {
            // preserve user's answer
            answer = request.getParameter("answer");
            answer = (answer == null) ? "" : answer;
        }
        
        // pull existing values out of session
        Integer value1o = (Integer)request.getSession().getAttribute("mathValue1");
        Integer value2o = (Integer)request.getSession().getAttribute("mathValue2");

        I18nMessages messages = I18nMessages.getMessages(request.getLocale());
        StringBuilder sb = new StringBuilder();
        
        sb.append("<p>");
        sb.append(messages.getString("comments.mathAuthenticatorQuestion"));
        sb.append("</p><p>");
        sb.append(value1o);
        sb.append(" + ");
        sb.append(value2o);
        sb.append(" = ");
        sb.append("<input name=\"answer\" value=\"");
        sb.append(answer);
        sb.append("\" /></p>");
        
        return sb.toString();
    }
    
    
    public boolean authenticate(HttpServletRequest request) {
        
        boolean authentic = false;
        
        HttpSession session = request.getSession(false);
        String answerString = request.getParameter("answer");
        
        if (answerString != null && session != null) {
            try {
                int answer = Integer.parseInt(answerString);
                Integer sum = (Integer) session.getAttribute("mathAnswer");
                
                if (sum != null && answer == sum) {
                    authentic = true;
                    session.removeAttribute("mathAnswer");
                    session.removeAttribute("mathValue1");
                    session.removeAttribute("mathValue2");
                }
            } catch (NumberFormatException ignored) {
                // ignored ... someone is just really bad at math
            } catch (Exception e) {
                // unexpected
                mLogger.error(e);
            }
        }
        
        return authentic;
    }
    
}

