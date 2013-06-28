
package org.flowframe.erp.integration.adaptors.services.soap;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for persistenceType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="persistenceType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ENTITY"/>
 *     &lt;enumeration value="EMBEDDABLE"/>
 *     &lt;enumeration value="MAPPED_SUPERCLASS"/>
 *     &lt;enumeration value="BASIC"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "persistenceType")
@XmlEnum
public enum PersistenceType {

    ENTITY,
    EMBEDDABLE,
    MAPPED_SUPERCLASS,
    BASIC;

    public String value() {
        return name();
    }

    public static PersistenceType fromValue(String v) {
        return valueOf(v);
    }

}
