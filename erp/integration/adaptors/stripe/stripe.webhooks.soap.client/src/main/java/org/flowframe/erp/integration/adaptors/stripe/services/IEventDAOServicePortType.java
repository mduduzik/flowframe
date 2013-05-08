package org.flowframe.erp.integration.adaptors.stripe.services;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.5.2
 * 2013-05-07T11:04:45.176-04:00
 * Generated source version: 2.5.2
 * 
 */
@WebService(targetNamespace = "http://services.stripe.adaptors.integration.erp.flowframe.org/", name = "IEventDAOServicePortType")
@XmlSeeAlso({ObjectFactory.class})
public interface IEventDAOServicePortType {

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "add", targetNamespace = "http://services.stripe.adaptors.integration.erp.flowframe.org/", className = "org.flowframe.erp.integration.adaptors.stripe.services.Add")
    @WebMethod
    @ResponseWrapper(localName = "addResponse", targetNamespace = "http://services.stripe.adaptors.integration.erp.flowframe.org/", className = "org.flowframe.erp.integration.adaptors.stripe.services.AddResponse")
    public org.flowframe.erp.integration.adaptors.stripe.services.Event add(
        @WebParam(name = "arg0", targetNamespace = "")
        org.flowframe.erp.integration.adaptors.stripe.services.Event arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getEventById", targetNamespace = "http://services.stripe.adaptors.integration.erp.flowframe.org/", className = "org.flowframe.erp.integration.adaptors.stripe.services.GetEventById")
    @WebMethod
    @ResponseWrapper(localName = "getEventByIdResponse", targetNamespace = "http://services.stripe.adaptors.integration.erp.flowframe.org/", className = "org.flowframe.erp.integration.adaptors.stripe.services.GetEventByIdResponse")
    public org.flowframe.erp.integration.adaptors.stripe.services.Event getEventById(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "provide", targetNamespace = "http://services.stripe.adaptors.integration.erp.flowframe.org/", className = "org.flowframe.erp.integration.adaptors.stripe.services.Provide")
    @WebMethod
    @ResponseWrapper(localName = "provideResponse", targetNamespace = "http://services.stripe.adaptors.integration.erp.flowframe.org/", className = "org.flowframe.erp.integration.adaptors.stripe.services.ProvideResponse")
    public org.flowframe.erp.integration.adaptors.stripe.services.Event provide(
        @WebParam(name = "arg0", targetNamespace = "")
        org.flowframe.erp.integration.adaptors.stripe.services.Event arg0
    ) throws Exception_Exception;

    @RequestWrapper(localName = "delete", targetNamespace = "http://services.stripe.adaptors.integration.erp.flowframe.org/", className = "org.flowframe.erp.integration.adaptors.stripe.services.Delete")
    @WebMethod
    @ResponseWrapper(localName = "deleteResponse", targetNamespace = "http://services.stripe.adaptors.integration.erp.flowframe.org/", className = "org.flowframe.erp.integration.adaptors.stripe.services.DeleteResponse")
    public void delete(
        @WebParam(name = "arg0", targetNamespace = "")
        org.flowframe.erp.integration.adaptors.stripe.services.Event arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "get", targetNamespace = "http://services.stripe.adaptors.integration.erp.flowframe.org/", className = "org.flowframe.erp.integration.adaptors.stripe.services.Get")
    @WebMethod
    @ResponseWrapper(localName = "getResponse", targetNamespace = "http://services.stripe.adaptors.integration.erp.flowframe.org/", className = "org.flowframe.erp.integration.adaptors.stripe.services.GetResponse")
    public org.flowframe.erp.integration.adaptors.stripe.services.Event get(
        @WebParam(name = "arg0", targetNamespace = "")
        long arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getAllInvoiceEventsCreated", targetNamespace = "http://services.stripe.adaptors.integration.erp.flowframe.org/", className = "org.flowframe.erp.integration.adaptors.stripe.services.GetAllInvoiceEventsCreated")
    @WebMethod
    @ResponseWrapper(localName = "getAllInvoiceEventsCreatedResponse", targetNamespace = "http://services.stripe.adaptors.integration.erp.flowframe.org/", className = "org.flowframe.erp.integration.adaptors.stripe.services.GetAllInvoiceEventsCreatedResponse")
    public java.util.List<org.flowframe.erp.integration.adaptors.stripe.services.Event> getAllInvoiceEventsCreated();

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getByCode", targetNamespace = "http://services.stripe.adaptors.integration.erp.flowframe.org/", className = "org.flowframe.erp.integration.adaptors.stripe.services.GetByCode")
    @WebMethod
    @ResponseWrapper(localName = "getByCodeResponse", targetNamespace = "http://services.stripe.adaptors.integration.erp.flowframe.org/", className = "org.flowframe.erp.integration.adaptors.stripe.services.GetByCodeResponse")
    public org.flowframe.erp.integration.adaptors.stripe.services.Event getByCode(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "update", targetNamespace = "http://services.stripe.adaptors.integration.erp.flowframe.org/", className = "org.flowframe.erp.integration.adaptors.stripe.services.Update")
    @WebMethod
    @ResponseWrapper(localName = "updateResponse", targetNamespace = "http://services.stripe.adaptors.integration.erp.flowframe.org/", className = "org.flowframe.erp.integration.adaptors.stripe.services.UpdateResponse")
    public org.flowframe.erp.integration.adaptors.stripe.services.Event update(
        @WebParam(name = "arg0", targetNamespace = "")
        org.flowframe.erp.integration.adaptors.stripe.services.Event arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getAllInvoiceEventsWithFailedPayment", targetNamespace = "http://services.stripe.adaptors.integration.erp.flowframe.org/", className = "org.flowframe.erp.integration.adaptors.stripe.services.GetAllInvoiceEventsWithFailedPayment")
    @WebMethod
    @ResponseWrapper(localName = "getAllInvoiceEventsWithFailedPaymentResponse", targetNamespace = "http://services.stripe.adaptors.integration.erp.flowframe.org/", className = "org.flowframe.erp.integration.adaptors.stripe.services.GetAllInvoiceEventsWithFailedPaymentResponse")
    public java.util.List<org.flowframe.erp.integration.adaptors.stripe.services.Event> getAllInvoiceEventsWithFailedPayment();

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getAllActiveEvents", targetNamespace = "http://services.stripe.adaptors.integration.erp.flowframe.org/", className = "org.flowframe.erp.integration.adaptors.stripe.services.GetAllActiveEvents")
    @WebMethod
    @ResponseWrapper(localName = "getAllActiveEventsResponse", targetNamespace = "http://services.stripe.adaptors.integration.erp.flowframe.org/", className = "org.flowframe.erp.integration.adaptors.stripe.services.GetAllActiveEventsResponse")
    public java.util.List<org.flowframe.erp.integration.adaptors.stripe.services.Event> getAllActiveEvents();

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getAll", targetNamespace = "http://services.stripe.adaptors.integration.erp.flowframe.org/", className = "org.flowframe.erp.integration.adaptors.stripe.services.GetAll")
    @WebMethod
    @ResponseWrapper(localName = "getAllResponse", targetNamespace = "http://services.stripe.adaptors.integration.erp.flowframe.org/", className = "org.flowframe.erp.integration.adaptors.stripe.services.GetAllResponse")
    public java.util.List<org.flowframe.erp.integration.adaptors.stripe.services.Event> getAll();

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getAllInvoiceEventsWithSuccessfulPayment", targetNamespace = "http://services.stripe.adaptors.integration.erp.flowframe.org/", className = "org.flowframe.erp.integration.adaptors.stripe.services.GetAllInvoiceEventsWithSuccessfulPayment")
    @WebMethod
    @ResponseWrapper(localName = "getAllInvoiceEventsWithSuccessfulPaymentResponse", targetNamespace = "http://services.stripe.adaptors.integration.erp.flowframe.org/", className = "org.flowframe.erp.integration.adaptors.stripe.services.GetAllInvoiceEventsWithSuccessfulPaymentResponse")
    public java.util.List<org.flowframe.erp.integration.adaptors.stripe.services.Event> getAllInvoiceEventsWithSuccessfulPayment();
}
