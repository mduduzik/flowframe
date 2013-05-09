
package org.flowframe.erp.integration.adaptors.stripe.services;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for event complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="event">
 *   &lt;complexContent>
 *     &lt;extension base="{http://services.stripe.adaptors.integration.erp.flowframe.org/}multitenantBaseEntity">
 *       &lt;sequence>
 *         &lt;element name="actionedWithSuccess" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="body" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dateLastTried" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="dateResponseSuccessReady" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="dateResponsedWithSuccess" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="eventCreated" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="eventType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lastReturnCode" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="livemode" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="objectId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="objectType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pendingWebhooks" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="processingTries" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="stripeId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "event", propOrder = {
    "actionedWithSuccess",
    "body",
    "dateLastTried",
    "dateResponseSuccessReady",
    "dateResponsedWithSuccess",
    "eventCreated",
    "eventType",
    "lastReturnCode",
    "livemode",
    "objectId",
    "objectType",
    "pendingWebhooks",
    "processingTries",
    "stripeId"
})
public class Event
    extends MultitenantBaseEntity
{

    protected Boolean actionedWithSuccess;
    protected String body;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateLastTried;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateResponseSuccessReady;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateResponsedWithSuccess;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar eventCreated;
    protected String eventType;
    protected Integer lastReturnCode;
    protected Boolean livemode;
    protected String objectId;
    protected String objectType;
    protected Integer pendingWebhooks;
    protected Integer processingTries;
    protected String stripeId;

    /**
     * Gets the value of the actionedWithSuccess property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isActionedWithSuccess() {
        return actionedWithSuccess;
    }

    /**
     * Sets the value of the actionedWithSuccess property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setActionedWithSuccess(Boolean value) {
        this.actionedWithSuccess = value;
    }

    /**
     * Gets the value of the body property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBody() {
        return body;
    }

    /**
     * Sets the value of the body property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBody(String value) {
        this.body = value;
    }

    /**
     * Gets the value of the dateLastTried property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateLastTried() {
        return dateLastTried;
    }

    /**
     * Sets the value of the dateLastTried property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateLastTried(XMLGregorianCalendar value) {
        this.dateLastTried = value;
    }

    /**
     * Gets the value of the dateResponseSuccessReady property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateResponseSuccessReady() {
        return dateResponseSuccessReady;
    }

    /**
     * Sets the value of the dateResponseSuccessReady property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateResponseSuccessReady(XMLGregorianCalendar value) {
        this.dateResponseSuccessReady = value;
    }

    /**
     * Gets the value of the dateResponsedWithSuccess property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateResponsedWithSuccess() {
        return dateResponsedWithSuccess;
    }

    /**
     * Sets the value of the dateResponsedWithSuccess property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateResponsedWithSuccess(XMLGregorianCalendar value) {
        this.dateResponsedWithSuccess = value;
    }

    /**
     * Gets the value of the eventCreated property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEventCreated() {
        return eventCreated;
    }

    /**
     * Sets the value of the eventCreated property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEventCreated(XMLGregorianCalendar value) {
        this.eventCreated = value;
    }

    /**
     * Gets the value of the eventType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEventType() {
        return eventType;
    }

    /**
     * Sets the value of the eventType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEventType(String value) {
        this.eventType = value;
    }

    /**
     * Gets the value of the lastReturnCode property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getLastReturnCode() {
        return lastReturnCode;
    }

    /**
     * Sets the value of the lastReturnCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setLastReturnCode(Integer value) {
        this.lastReturnCode = value;
    }

    /**
     * Gets the value of the livemode property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isLivemode() {
        return livemode;
    }

    /**
     * Sets the value of the livemode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setLivemode(Boolean value) {
        this.livemode = value;
    }

    /**
     * Gets the value of the objectId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObjectId() {
        return objectId;
    }

    /**
     * Sets the value of the objectId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObjectId(String value) {
        this.objectId = value;
    }

    /**
     * Gets the value of the objectType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObjectType() {
        return objectType;
    }

    /**
     * Sets the value of the objectType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObjectType(String value) {
        this.objectType = value;
    }

    /**
     * Gets the value of the pendingWebhooks property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPendingWebhooks() {
        return pendingWebhooks;
    }

    /**
     * Sets the value of the pendingWebhooks property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPendingWebhooks(Integer value) {
        this.pendingWebhooks = value;
    }

    /**
     * Gets the value of the processingTries property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getProcessingTries() {
        return processingTries;
    }

    /**
     * Sets the value of the processingTries property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setProcessingTries(Integer value) {
        this.processingTries = value;
    }

    /**
     * Gets the value of the stripeId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStripeId() {
        return stripeId;
    }

    /**
     * Sets the value of the stripeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStripeId(String value) {
        this.stripeId = value;
    }

}
