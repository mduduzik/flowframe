
package org.flowframe.erp.integration.adaptors.stripe.services;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for noteItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="noteItem">
 *   &lt;complexContent>
 *     &lt;extension base="{http://services.stripe.adaptors.integration.erp.flowframe.org/}multitenantBaseEntity">
 *       &lt;sequence>
 *         &lt;element name="content" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="noteType" type="{http://services.stripe.adaptors.integration.erp.flowframe.org/}noteType" minOccurs="0"/>
 *         &lt;element name="parentNote" type="{http://services.stripe.adaptors.integration.erp.flowframe.org/}note" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "noteItem", propOrder = {
    "content",
    "noteType",
    "parentNote"
})
public class NoteItem
    extends MultitenantBaseEntity
{

    protected String content;
    protected NoteType noteType;
    protected Note parentNote;

    /**
     * Gets the value of the content property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the value of the content property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContent(String value) {
        this.content = value;
    }

    /**
     * Gets the value of the noteType property.
     * 
     * @return
     *     possible object is
     *     {@link NoteType }
     *     
     */
    public NoteType getNoteType() {
        return noteType;
    }

    /**
     * Sets the value of the noteType property.
     * 
     * @param value
     *     allowed object is
     *     {@link NoteType }
     *     
     */
    public void setNoteType(NoteType value) {
        this.noteType = value;
    }

    /**
     * Gets the value of the parentNote property.
     * 
     * @return
     *     possible object is
     *     {@link Note }
     *     
     */
    public Note getParentNote() {
        return parentNote;
    }

    /**
     * Sets the value of the parentNote property.
     * 
     * @param value
     *     allowed object is
     *     {@link Note }
     *     
     */
    public void setParentNote(Note value) {
        this.parentNote = value;
    }

}
