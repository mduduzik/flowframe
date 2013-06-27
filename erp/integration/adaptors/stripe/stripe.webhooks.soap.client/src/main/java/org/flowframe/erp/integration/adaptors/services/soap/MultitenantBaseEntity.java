
package org.flowframe.erp.integration.adaptors.services.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for multitenantBaseEntity complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="multitenantBaseEntity">
 *   &lt;complexContent>
 *     &lt;extension base="{http://services.stripe.adaptors.integration.erp.flowframe.org/}baseEntity">
 *       &lt;sequence>
 *         &lt;element name="tenant" type="{http://services.stripe.adaptors.integration.erp.flowframe.org/}organization" minOccurs="0"/>
 *         &lt;element name="tenantWide" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "multitenantBaseEntity", propOrder = {
    "tenant",
    "tenantWide"
})
@XmlSeeAlso({
    DocType.class,
    NoteType.class,
    Event.class,
    EntityPreferenceItemOption.class,
    AddressType.class,
    EntityPreference.class,
    NoteItem.class,
    ContactType.class,
    EntityPreferenceItem.class,
    Organization.class,
    AddressTypeAddress.class,
    Note.class,
    ContactTypeContact.class
})
public abstract class MultitenantBaseEntity
    extends BaseEntity
{

    protected Organization tenant;
    protected boolean tenantWide;

    /**
     * Gets the value of the tenant property.
     * 
     * @return
     *     possible object is
     *     {@link Organization }
     *     
     */
    public Organization getTenant() {
        return tenant;
    }

    /**
     * Sets the value of the tenant property.
     * 
     * @param value
     *     allowed object is
     *     {@link Organization }
     *     
     */
    public void setTenant(Organization value) {
        this.tenant = value;
    }

    /**
     * Gets the value of the tenantWide property.
     * 
     */
    public boolean isTenantWide() {
        return tenantWide;
    }

    /**
     * Sets the value of the tenantWide property.
     * 
     */
    public void setTenantWide(boolean value) {
        this.tenantWide = value;
    }

}
