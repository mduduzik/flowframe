
package org.flowframe.erp.integration.adaptors.services.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for unloco complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="unloco">
 *   &lt;complexContent>
 *     &lt;extension base="{http://services.stripe.adaptors.integration.erp.flowframe.org/}baseEntity">
 *       &lt;sequence>
 *         &lt;element name="country" type="{http://services.stripe.adaptors.integration.erp.flowframe.org/}country" minOccurs="0"/>
 *         &lt;element name="countryState" type="{http://services.stripe.adaptors.integration.erp.flowframe.org/}countryState" minOccurs="0"/>
 *         &lt;element name="portCity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "unloco", propOrder = {
    "country",
    "countryState",
    "portCity"
})
public class Unloco
    extends BaseEntity
{

    protected Country country;
    protected CountryState countryState;
    protected String portCity;

    /**
     * Gets the value of the country property.
     * 
     * @return
     *     possible object is
     *     {@link Country }
     *     
     */
    public Country getCountry() {
        return country;
    }

    /**
     * Sets the value of the country property.
     * 
     * @param value
     *     allowed object is
     *     {@link Country }
     *     
     */
    public void setCountry(Country value) {
        this.country = value;
    }

    /**
     * Gets the value of the countryState property.
     * 
     * @return
     *     possible object is
     *     {@link CountryState }
     *     
     */
    public CountryState getCountryState() {
        return countryState;
    }

    /**
     * Sets the value of the countryState property.
     * 
     * @param value
     *     allowed object is
     *     {@link CountryState }
     *     
     */
    public void setCountryState(CountryState value) {
        this.countryState = value;
    }

    /**
     * Gets the value of the portCity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPortCity() {
        return portCity;
    }

    /**
     * Sets the value of the portCity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPortCity(String value) {
        this.portCity = value;
    }

}
