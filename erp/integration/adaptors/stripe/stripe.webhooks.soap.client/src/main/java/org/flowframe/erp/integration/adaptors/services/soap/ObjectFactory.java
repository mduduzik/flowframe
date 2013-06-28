
package org.flowframe.erp.integration.adaptors.services.soap;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.flowframe.erp.integration.adaptors.services.soap package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Exception_QNAME = new QName("http://services.stripe.adaptors.integration.erp.flowframe.org/", "Exception");
    private final static QName _GreenLightEventResponse_QNAME = new QName("http://services.stripe.adaptors.integration.erp.flowframe.org/", "greenLightEventResponse");
    private final static QName _ProcessEventResponse_QNAME = new QName("http://services.stripe.adaptors.integration.erp.flowframe.org/", "processEventResponse");
    private final static QName _MarkInactive_QNAME = new QName("http://services.stripe.adaptors.integration.erp.flowframe.org/", "markInactive");
    private final static QName _MarkInactiveResponse_QNAME = new QName("http://services.stripe.adaptors.integration.erp.flowframe.org/", "markInactiveResponse");
    private final static QName _GreenLightEvent_QNAME = new QName("http://services.stripe.adaptors.integration.erp.flowframe.org/", "greenLightEvent");
    private final static QName _ProcessEvent_QNAME = new QName("http://services.stripe.adaptors.integration.erp.flowframe.org/", "processEvent");
    private final static QName _MarkActive_QNAME = new QName("http://services.stripe.adaptors.integration.erp.flowframe.org/", "markActive");
    private final static QName _MarkActiveResponse_QNAME = new QName("http://services.stripe.adaptors.integration.erp.flowframe.org/", "markActiveResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.flowframe.erp.integration.adaptors.services.soap
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link MarkActiveResponse }
     * 
     */
    public MarkActiveResponse createMarkActiveResponse() {
        return new MarkActiveResponse();
    }

    /**
     * Create an instance of {@link MarkInactiveResponse }
     * 
     */
    public MarkInactiveResponse createMarkInactiveResponse() {
        return new MarkInactiveResponse();
    }

    /**
     * Create an instance of {@link GreenLightEventResponse }
     * 
     */
    public GreenLightEventResponse createGreenLightEventResponse() {
        return new GreenLightEventResponse();
    }

    /**
     * Create an instance of {@link ProcessEventResponse }
     * 
     */
    public ProcessEventResponse createProcessEventResponse() {
        return new ProcessEventResponse();
    }

    /**
     * Create an instance of {@link MarkActive }
     * 
     */
    public MarkActive createMarkActive() {
        return new MarkActive();
    }

    /**
     * Create an instance of {@link ProcessEvent }
     * 
     */
    public ProcessEvent createProcessEvent() {
        return new ProcessEvent();
    }

    /**
     * Create an instance of {@link Exception }
     * 
     */
    public Exception createException() {
        return new Exception();
    }

    /**
     * Create an instance of {@link GreenLightEvent }
     * 
     */
    public GreenLightEvent createGreenLightEvent() {
        return new GreenLightEvent();
    }

    /**
     * Create an instance of {@link MarkInactive }
     * 
     */
    public MarkInactive createMarkInactive() {
        return new MarkInactive();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Exception }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.stripe.adaptors.integration.erp.flowframe.org/", name = "Exception")
    public JAXBElement<Exception> createException(Exception value) {
        return new JAXBElement<Exception>(_Exception_QNAME, Exception.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GreenLightEventResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.stripe.adaptors.integration.erp.flowframe.org/", name = "greenLightEventResponse")
    public JAXBElement<GreenLightEventResponse> createGreenLightEventResponse(GreenLightEventResponse value) {
        return new JAXBElement<GreenLightEventResponse>(_GreenLightEventResponse_QNAME, GreenLightEventResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ProcessEventResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.stripe.adaptors.integration.erp.flowframe.org/", name = "processEventResponse")
    public JAXBElement<ProcessEventResponse> createProcessEventResponse(ProcessEventResponse value) {
        return new JAXBElement<ProcessEventResponse>(_ProcessEventResponse_QNAME, ProcessEventResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MarkInactive }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.stripe.adaptors.integration.erp.flowframe.org/", name = "markInactive")
    public JAXBElement<MarkInactive> createMarkInactive(MarkInactive value) {
        return new JAXBElement<MarkInactive>(_MarkInactive_QNAME, MarkInactive.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MarkInactiveResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.stripe.adaptors.integration.erp.flowframe.org/", name = "markInactiveResponse")
    public JAXBElement<MarkInactiveResponse> createMarkInactiveResponse(MarkInactiveResponse value) {
        return new JAXBElement<MarkInactiveResponse>(_MarkInactiveResponse_QNAME, MarkInactiveResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GreenLightEvent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.stripe.adaptors.integration.erp.flowframe.org/", name = "greenLightEvent")
    public JAXBElement<GreenLightEvent> createGreenLightEvent(GreenLightEvent value) {
        return new JAXBElement<GreenLightEvent>(_GreenLightEvent_QNAME, GreenLightEvent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ProcessEvent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.stripe.adaptors.integration.erp.flowframe.org/", name = "processEvent")
    public JAXBElement<ProcessEvent> createProcessEvent(ProcessEvent value) {
        return new JAXBElement<ProcessEvent>(_ProcessEvent_QNAME, ProcessEvent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MarkActive }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.stripe.adaptors.integration.erp.flowframe.org/", name = "markActive")
    public JAXBElement<MarkActive> createMarkActive(MarkActive value) {
        return new JAXBElement<MarkActive>(_MarkActive_QNAME, MarkActive.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MarkActiveResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.stripe.adaptors.integration.erp.flowframe.org/", name = "markActiveResponse")
    public JAXBElement<MarkActiveResponse> createMarkActiveResponse(MarkActiveResponse value) {
        return new JAXBElement<MarkActiveResponse>(_MarkActiveResponse_QNAME, MarkActiveResponse.class, null, value);
    }

}
