
@XmlSchema(
	namespace="urn:katerkamp:schemas:cps"
	,xmlns={ 
		@XmlNs(prefix = "", namespaceURI = "urn:katerkamp:schemas:cps"),
		@XmlNs(prefix = "xsi", namespaceURI = "http://www.w3.org/2001/XMLSchema http://www.w3.org/2001/XMLSchema.xsd"),
	}
	,elementFormDefault=XmlNsForm.UNQUALIFIED
)


package de.katerkamp.cps.plug;

import javax.xml.bind.annotation.XmlNsForm;
import javax.xml.bind.annotation.XmlSchema;
import javax.xml.bind.annotation.XmlNs;
