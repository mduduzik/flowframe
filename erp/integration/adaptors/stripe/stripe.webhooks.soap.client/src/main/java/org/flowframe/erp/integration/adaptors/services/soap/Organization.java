
package org.flowframe.erp.integration.adaptors.services.soap;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for organization complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="organization">
 *   &lt;complexContent>
 *     &lt;extension base="{http://services.stripe.adaptors.integration.erp.flowframe.org/}multitenantBaseEntity">
 *       &lt;sequence>
 *         &lt;element name="addressTypeAddresses" type="{http://services.stripe.adaptors.integration.erp.flowframe.org/}addressTypeAddress" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="contactTypeContacts" type="{http://services.stripe.adaptors.integration.erp.flowframe.org/}contactTypeContact" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="organizationId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="parentOrganizationId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "organization", propOrder = {
    "addressTypeAddresses",
    "contactTypeContacts",
    "organizationId",
    "parentOrganizationId",
    "type"
})
public class Organization
    extends MultitenantBaseEntity
{

    @XmlElement(nillable = true)
    protected List<AddressTypeAddress> addressTypeAddresses;
    @XmlElement(nillable = true)
    protected List<ContactTypeContact> contactTypeContacts;
    protected long organizationId;
    protected long parentOrganizationId;
    protected String type;

    /**
     * Gets the value of the addressTypeAddresses property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the addressTypeAddresses property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAddressTypeAddresses().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AddressTypeAddress }
     * 
     * 
     */
    public List<AddressTypeAddress> getAddressTypeAddresses() {
        if (addressTypeAddresses == null) {
            addressTypeAddresses = new ArrayList<AddressTypeAddress>();
        }
        return this.addressTypeAddresses;
    }

    /**
     * Gets the value of the contactTypeContacts property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the contactTypeContacts property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContactTypeContacts().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ContactTypeContact }
     * 
     * 
     */
    public List<ContactTypeContact> getContactTypeContacts() {
        if (contactTypeContacts == null) {
            contactTypeContacts = new ArrayList<ContactTypeContact>();
        }
        return this.contactTypeContacts;
    }

    /**
     * Gets the value of the organizationId property.
     * 
     */
    public long getOrganizationId() {
        return organizationId;
    }

    /**
     * Sets the value of the organizationId property.
     * 
     */
    public void setOrganizationId(long value) {
        this.organizationId = value;
    }

    /**
     * Gets the value of the parentOrganizationId property.
     * 
     */
    public long getParentOrganizationId() {
        return parentOrganizationId;
    }

    /**
     * Sets the value of the parentOrganizationId property.
     * 
     */
    public void setParentOrganizationId(long value) {
        this.parentOrganizationId = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

}
