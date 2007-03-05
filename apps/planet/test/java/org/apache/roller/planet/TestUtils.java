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

package org.apache.roller.planet;

import org.apache.roller.RollerException;
import org.apache.roller.planet.business.PlanetFactory;
import org.apache.roller.planet.business.PlanetManager;
import org.apache.roller.planet.pojos.PlanetData;


/**
 * Utility class for unit test classes.
 */
public final class TestUtils {
    
    
    /**
     * Convenience method that simulates the end of a typical session.
     *
     * Normally this would be triggered by the end of the response in the webapp
     * but for unit tests we need to do this explicitly.
     *
     * @param flush true if you want to flush changes to db before releasing
     */
    public static void endSession(boolean flush) throws Exception {
        
        if(flush) {
            PlanetFactory.getPlanet().flush();
        }
        
        PlanetFactory.getPlanet().release();
    }
    
    
    /**
     * Convenience method that creates a planet and stores it.
     */
    public static PlanetData setupPlanet(String handle) throws Exception {
        
        PlanetData testPlanet = new PlanetData(handle, handle);
        
        // store
        PlanetManager mgr = PlanetFactory.getPlanet().getPlanetManager();
        mgr.savePlanet(testPlanet);
        
        // flush
        PlanetFactory.getPlanet().flush();
        
        // query to make sure we return the persisted object
        PlanetData planet = mgr.getPlanet(handle);
        
        if(planet == null)
            throw new RollerException("error inserting new planet");
        
        return planet;
    }
    
    
    /**
     * Convenience method for removing a planet.
     */
    public static void teardownPlanet(String id) throws Exception {
        
        // lookup
        PlanetManager mgr = PlanetFactory.getPlanet().getPlanetManager();
        PlanetData planet = mgr.getPlanetById(id);
        
        // remove
        mgr.deletePlanet(planet);
        
        // flush
        PlanetFactory.getPlanet().flush();
    }
    
}