
package org.flowframe.erp.integration.adaptors.services.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for entityPreferenceItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="entityPreferenceItem">
 *   &lt;complexContent>
 *     &lt;extension base="{http://services.stripe.adaptors.integration.erp.flowframe.org/}multitenantBaseEntity">
 *       &lt;sequence>
 *         &lt;element name="basicType" type="{http://services.stripe.adaptors.integration.erp.flowframe.org/}basicType" minOccurs="0"/>
 *         &lt;element name="hidden" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="option" type="{http://services.stripe.adaptors.integration.erp.flowframe.org/}entityPreferenceItemOption" minOccurs="0"/>
 *         &lt;element name="ordinal" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="parentEntityPreference" type="{http://services.stripe.adaptors.integration.erp.flowframe.org/}entityPreference" minOccurs="0"/>
 *         &lt;element name="preferenceKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="preferenceValue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="readOnly" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="size" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="typedValue" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "entityPreferenceItem", propOrder = {
    "basicType",
    "hidden",
    "option",
    "ordinal",
    "parentEntityPreference",
    "preferenceKey",
    "preferenceValue",
    "readOnly",
    "size",
    "typedValue"
})
public class EntityPreferenceItem
    extends MultitenantBaseEntity
{

    protected BasicType basicType;
    protected Boolean hidden;
    protected EntityPreferenceItemOption option;
    protected int ordinal;
    protected EntityPreference parentEntityPreference;
    protected String preferenceKey;
    protected String preferenceValue;
    protected Boolean readOnly;
    protected int size;
    protected Object typedValue;

    /**
     * Gets the value of the basicType property.
     * 
     * @return
     *     possible object is
     *     {@link BasicType }
     *     
     */
    public BasicType getBasicType() {
        return basicType;
    }

    /**
     * Sets the value of the basicType property.
     * 
     * @param value
     *     allowed object is
     *     {@link BasicType }
     *     
     */
    public void setBasicType(BasicType value) {
        this.basicType = value;
    }

    /**
     * Gets the value of the hidden property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isHidden() {
        return hidden;
    }

    /**
     * Sets the value of the hidden property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setHidden(Boolean value) {
        this.hidden = value;
    }

    /**
     * Gets the value of the option property.
     * 
     * @return
     *     possible object is
     *     {@link EntityPreferenceItemOption }
     *     
     */
    public EntityPreferenceItemOption getOption() {
        return option;
    }

    /**
     * Sets the value of the option property.
     * 
     * @param value
     *     allowed object is
     *     {@link EntityPreferenceItemOption }
     *     
     */
    public void setOption(EntityPreferenceItemOption value) {
        this.option = value;
    }

    /**
     * Gets the value of the ordinal property.
     * 
     */
    public int getOrdinal() {
        return ordinal;
    }

    /**
     * Sets the value of the ordinal property.
     * 
     */
    public void setOrdinal(int value) {
        this.ordinal = value;
    }

    /**
     * Gets the value of the parentEntityPreference property.
     * 
     * @return
     *     possible object is
     *     {@link EntityPreference }
     *     
     */
    public EntityPreference getParentEntityPreference() {
        return parentEntityPreference;
    }

    /**
     * Sets the value of the parentEntityPreference property.
     * 
     * @param value
     *     allowed object is
     *     {@link EntityPreference }
     *     
     */
    public void setParentEntityPreference(EntityPreference value) {
        this.parentEntityPreference = value;
    }

    /**
     * Gets the value of the preferenceKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPreferenceKey() {
        return preferenceKey;
    }

    /**
     * Sets the value of the preferenceKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPreferenceKey(String value) {
        this.preferenceKey = value;
    }

    /**
     * Gets the value of the preferenceValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPreferenceValue() {
        return preferenceValue;
    }

    /**
     * Sets the value of the preferenceValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPreferenceValue(String value) {
        this.preferenceValue = value;
    }

    /**
     * Gets the value of the readOnly property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isReadOnly() {
        return readOnly;
    }

    /**
     * Sets the value of the readOnly property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setReadOnly(Boolean value) {
        this.readOnly = value;
    }

    /**
     * Gets the value of the size property.
     * 
     */
    public int getSize() {
        return size;
    }

    /**
     * Sets the value of the size property.
     * 
     */
    public void setSize(int value) {
        this.size = value;
    }

    /**
     * Gets the value of the typedValue property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getTypedValue() {
        return typedValue;
    }

    /**
     * Sets the value of the typedValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setTypedValue(Object value) {
        this.typedValue = value;
    }

}
