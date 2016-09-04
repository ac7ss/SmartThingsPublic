/**
 *  HVAC Unnecessary
 *
 *  Copyright 2016 Glenn Brockett
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
definition(
    name: "HVAC Unnecessary",
    namespace: "ac7ss",
    author: "Glenn Brockett",
    description: "Turn off the AC when thermostat set point is warmer than outside temperature",
    category: "Green Living",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png")


preferences {
	section("Settings") {
		// TODO: put inputs here
        input "OutsideTemp", "capability.temperatureMeasurement", title: "Outside Thermometer:", required: true
		input "Thermostat", "capability.thermostat", title: "Thermostat:", required: true
	}
}

def installed() {
	log.debug "Installed with settings: ${settings}"

	initialize()
}

def updated() {
	log.debug "Updated with settings: ${settings}"

	unsubscribe()
	initialize()
}

def initialize() {
	// TODO: subscribe to attributes, devices, locations, etc.
   	subscribe(OutsideTemp, "temperature", myHandler);
	subscribe(Thermostat, "coolingSetpoint", myHandler);

}

// TODO: implement event handlers

def myHandler(evt){
	def CSP = Thermostat.latestValue("coolingSetpoint") as float
    def HSP = Thermostat.latestValue("heatingSetpoint") as float
    def OT = OutsideTemp.latestValue("temperature") as float
    log.debug " Run CSP ${CSP} HSP ${HSP} CT ${CT} "
	if ((CSP > OT)&&
    (HST < OT)){
    	Thermostat.setThermostatMode("off")
    }else {
    	Thermostat.setThermostatMode("on")
    }
}