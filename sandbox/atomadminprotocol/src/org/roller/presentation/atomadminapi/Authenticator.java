/*
 * Copyright 2005 David M Johnson (For RSS and Atom In Action)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.roller.presentation.atomadminapi;

import javax.servlet.http.HttpServletRequest;
import org.roller.model.Roller;
import org.roller.model.RollerFactory;

/**
 * TODO
 *
 * @author jtb
 */
abstract class Authenticator {   
    private HttpServletRequest request;
    private Roller             roller;
    private String             userId;
    
    /** Creates a new instance of HttpBasicAuthenticator */
    public Authenticator(HttpServletRequest req) {
        setRequest(req);
        setRoller(RollerFactory.getRoller());
    }
    
    public abstract boolean authenticate();    

    public HttpServletRequest getRequest() {
        return request;
    }

    protected void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public String getUserId() {
        return userId;
    }

    protected void setUserId(String userId) {
        this.userId = userId;
    }

    protected Roller getRoller() {
        return roller;
    }

    protected void setRoller(Roller roller) {
        this.roller = roller;
    }
}