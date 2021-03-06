/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.modelo.dto.ventas.facturacion;

import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.activation.FileDataSource;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.Transform;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import pe.modelo.pojo.DocumentoVenta;
import pe.modelo.pojo.DocumentoVentaDetalle;
import pe.modelo.pojo.EstadoDocumentoVenta;

public class Factura {

    private Document document;
    private String fileName;
    private BillService_Service service;
    private BillService port;
    private String rootFile;
    private DocumentoVenta documentoVenta;

    public Factura() {
    }

    public void generarCaso(DocumentoVenta documentoVenta) throws IOException {
        //rootFile = root;
        this.documentoVenta = documentoVenta;
        generarDom();
        generarDocumento();
        generarXml();
        firmarXml();
        generarZip();
        inicializarServicioWeb();
        agregarHeaderSecurity();
        sendBill();
    }

    private void generarDom() {
        try {
            DocumentBuilderFactory factoty = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factoty.newDocumentBuilder();
            factoty.setNamespaceAware(true);
            document = builder.newDocument();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Factura.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void generarDocumento() {
        fileName = pe.modelo.Configuracion.rutaFacturas
                + documentoVenta.getEmpresa().getEntidad().getDocumento() + "-"
                + documentoVenta.getTipoDocumentoVenta().getCodigo() + "-"
                + documentoVenta.getPuntoVentaSerie().getCodigo() + "-"
                + documentoVenta.getNumero();
        //dccIdGeneral = documentoVenta.getId();
        String idGravado = "1001";
        String dccTotalGravado = Double.toString(pe.modelo.Configuracion.redondearDecimales(documentoVenta.getTotalGrabado(), 2));
        String idInafecto = "1002";
        String dccTotalInafecto = Double.toString(pe.modelo.Configuracion.redondearDecimales(0, 2));// factura.getString("dccTotalInafecto");
        String idExonerado = "1003";
        String dccTotalExonerado = Double.toString(pe.modelo.Configuracion.redondearDecimales(0, 2));//factura.getString("dccTotalExonerado");
        String idGratuito = "1004";
        String dccTotalGratuito = Double.toString(pe.modelo.Configuracion.redondearDecimales(documentoVenta.getTotalGratuito(), 2));// factura.getString("dccTotalGratuito");
        String idDescuento = "2005";
        String dccTotalDescuento = Double.toString(pe.modelo.Configuracion.redondearDecimales(documentoVenta.getTotalDescuento(), 2));// factura.getString("dccTotalDescuento");
        String idMontoLetras = "1000";
        String dccTotalVentaLetras = documentoVenta.getTotalLetras();// factura.getString("dccTotalVentaLetras");

        //System.out.println("documentoVenta.getFechaEmision():" + documentoVenta.getFechaEmision());
        //System.out.println("documentoVenta.getFechaEmision():" + pe.modelo.Configuracion.simpleDateFormat.format(documentoVenta.getFechaEmision()));
        String dccFechaEmision = documentoVenta.getFechaEmision().toString();// factura.getString("dccFechaEmision");//yyyy-mm-dd

        String empRazonSocial = pe.modelo.Configuracion.reemplazarCaracteresParaSunat(documentoVenta.getEmpresa().getEntidad().getNombre());
        String empRuc = documentoVenta.getEmpresa().getEntidad().getDocumento();
        String empTipoEntidad = documentoVenta.getEmpresa().getEntidad().getTipoDocumentoEntidad().getCodigo();
        String ubiCodigo = documentoVenta.getEmpresa().getEntidad().getUbigeo().getCodigo();
        String empDireccion = documentoVenta.getEmpresa().getEntidad().getDireccion();
        String empZona = documentoVenta.getEmpresa().getEntidad().getZona();
        String empDistrito = documentoVenta.getEmpresa().getEntidad().getUbigeo().getNombre();
        String empProvincia = documentoVenta.getEmpresa().getEntidad().getUbigeo().getNombreProvincia();
        String empDepartamento = documentoVenta.getEmpresa().getEntidad().getUbigeo().getNombreDepartamento();
        String empCodigoPais = documentoVenta.getEmpresa().getEntidad().getCodigoPais();

        String tdcCodigo = documentoVenta.getTipoDocumentoVenta().getCodigo();

        //Numeración, conformada por serie y número correlativo
        String numeracion = documentoVenta.getPuntoVentaSerie().getCodigo() + "-" + documentoVenta.getNumero();

        //Tipo y número de documento de identidad del adquirente o usuario
        //Apellidos y nombres o denominación o razón social del adquirente o usuario
        String entNombre = pe.modelo.Configuracion.reemplazarCaracteresParaSunat(documentoVenta.getCliente().getEntidad().getNombre());
        String entDocumento = documentoVenta.getCliente().getEntidad().getDocumento();
        String tdeCodigo = documentoVenta.getCliente().getEntidad().getTipoDocumentoEntidad().getCodigo();

        //Tipo de moneda en la cual se emite la factura electrónica
        String monCodigo = documentoVenta.getMoneda().getCodigo();//PEN:Nuevo Sol
        //Versión del UBL
        String ubl = "2.0";
        //Versión de la estructura del documento
        String vDocomento = "1.0";

        //IGV total
        String dccTotalIgv = Double.toString(pe.modelo.Configuracion.redondearDecimales(documentoVenta.getTotalIgv(), 2));
        //Importe total de la venta, de la cesión en uso o del servicio prestado
        String dccTotalVenta = Double.toString(pe.modelo.Configuracion.redondearDecimales(documentoVenta.getTotal(), 2));

        org.w3c.dom.Element invoice = document.createElement("Invoice");
        invoice.setAttribute("xmlns", "urn:oasis:names:specification:ubl:schema:xsd:Invoice-2");
        invoice.setAttribute("xmlns:cac", "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2");
        invoice.setAttribute("xmlns:cbc", "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2");
        invoice.setAttribute("xmlns:ccts", "urn:un:unece:uncefact:documentation:2");
        invoice.setAttribute("xmlns:ds", "http://www.w3.org/2000/09/xmldsig#");
        invoice.setAttribute("xmlns:ext", "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2");
        invoice.setAttribute("xmlns:qdt", "urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2");
        invoice.setAttribute("xmlns:sac", "urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1");
        invoice.setAttribute("xmlns:udt", "urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2");
        invoice.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
        document.appendChild(invoice);

        //INICIO 18. Total valor de venta - operaciones gravadas
        org.w3c.dom.Element extUBLExtensions = document.createElement("ext:UBLExtensions");
        invoice.appendChild(extUBLExtensions);

        org.w3c.dom.Element extUBLExtension = document.createElement("ext:UBLExtension");
        extUBLExtensions.appendChild(extUBLExtension);

        org.w3c.dom.Element extExtensionContent = document.createElement("ext:ExtensionContent");
        extUBLExtension.appendChild(extExtensionContent);

        org.w3c.dom.Element sacAdditionalInformation = document.createElement("sac:AdditionalInformation");
        extExtensionContent.appendChild(sacAdditionalInformation);

        org.w3c.dom.Element sacAdditionalMonetaryTotal = document.createElement("sac:AdditionalMonetaryTotal");
        sacAdditionalInformation.appendChild(sacAdditionalMonetaryTotal);

        org.w3c.dom.Element cbcID = document.createElement("cbc:ID");
        cbcID.appendChild(document.createTextNode(idGravado));
        sacAdditionalMonetaryTotal.appendChild(cbcID);

        org.w3c.dom.Element cbcPayableAmount = document.createElement("cbc:PayableAmount");
        cbcPayableAmount.setAttribute("currencyID", monCodigo);
        cbcPayableAmount.appendChild(document.createTextNode(dccTotalGravado));
        sacAdditionalMonetaryTotal.appendChild(cbcPayableAmount);

        org.w3c.dom.Element sacAdditionalMonetaryTotal_2 = document.createElement("sac:AdditionalMonetaryTotal");
        sacAdditionalInformation.appendChild(sacAdditionalMonetaryTotal_2);

        org.w3c.dom.Element cbcID_2 = document.createElement("cbc:ID");
        cbcID_2.appendChild(document.createTextNode(idInafecto));
        sacAdditionalMonetaryTotal_2.appendChild(cbcID_2);

        org.w3c.dom.Element cbcPayableAmount_3 = document.createElement("cbc:PayableAmount");
        cbcPayableAmount_3.setAttribute("currencyID", monCodigo);
        cbcPayableAmount_3.appendChild(document.createTextNode(dccTotalInafecto));
        sacAdditionalMonetaryTotal_2.appendChild(cbcPayableAmount_3);

        org.w3c.dom.Element sacAdditionalMonetaryTotal_3 = document.createElement("sac:AdditionalMonetaryTotal");
        sacAdditionalInformation.appendChild(sacAdditionalMonetaryTotal_3);

        org.w3c.dom.Element cbcID_8 = document.createElement("cbc:ID");
        cbcID_8.appendChild(document.createTextNode(idExonerado));
        sacAdditionalMonetaryTotal_3.appendChild(cbcID_8);

        org.w3c.dom.Element cbcPayableAmount_4 = document.createElement("cbc:PayableAmount");
        cbcPayableAmount_4.setAttribute("currencyID", monCodigo);
        cbcPayableAmount_4.appendChild(document.createTextNode(dccTotalExonerado));
        sacAdditionalMonetaryTotal_3.appendChild(cbcPayableAmount_4);

        org.w3c.dom.Element sacAdditionalMonetaryTotal_4 = document.createElement("sac:AdditionalMonetaryTotal");
        sacAdditionalInformation.appendChild(sacAdditionalMonetaryTotal_4);

        org.w3c.dom.Element cbcID_9 = document.createElement("cbc:ID");
        cbcID_9.appendChild(document.createTextNode(idGratuito));
        sacAdditionalMonetaryTotal_4.appendChild(cbcID_9);

        org.w3c.dom.Element cbcPayableAmount_5 = document.createElement("cbc:PayableAmount");
        cbcPayableAmount_5.setAttribute("currencyID", monCodigo);
        cbcPayableAmount_5.appendChild(document.createTextNode(dccTotalGratuito));
        sacAdditionalMonetaryTotal_4.appendChild(cbcPayableAmount_5);

        org.w3c.dom.Element sacAdditionalMonetaryTotal_5 = document.createElement("sac:AdditionalMonetaryTotal");
        sacAdditionalInformation.appendChild(sacAdditionalMonetaryTotal_5);

        org.w3c.dom.Element cbcID_10 = document.createElement("cbc:ID");
        cbcID_10.appendChild(document.createTextNode(idDescuento));
        sacAdditionalMonetaryTotal_5.appendChild(cbcID_10);

        org.w3c.dom.Element cbcPayableAmount_6 = document.createElement("cbc:PayableAmount");
        cbcPayableAmount_6.setAttribute("currencyID", monCodigo);
        cbcPayableAmount_6.appendChild(document.createTextNode(dccTotalDescuento));
        sacAdditionalMonetaryTotal_5.appendChild(cbcPayableAmount_6);
        //
        org.w3c.dom.Element sacAdditionalProperty = document.createElement("sac:AdditionalProperty");
        sacAdditionalInformation.appendChild(sacAdditionalProperty);

        org.w3c.dom.Element cbcID_11 = document.createElement("cbc:ID");
        cbcID_11.appendChild(document.createTextNode(idMontoLetras));
        sacAdditionalProperty.appendChild(cbcID_11);

        org.w3c.dom.Element cbcValue = document.createElement("cbc:Value");
        cbcValue.appendChild(document.createTextNode(dccTotalVentaLetras));
        sacAdditionalProperty.appendChild(cbcValue);
        //FIN 18º
        //INICIO 2. Firma Digital. Obligatorio.
        org.w3c.dom.Element extUBLExtension_2 = document.createElement("ext:UBLExtension");
        extUBLExtensions.appendChild(extUBLExtension_2);

        org.w3c.dom.Element extExtensionContent_2 = document.createElement("ext:ExtensionContent");
        extUBLExtension_2.appendChild(extExtensionContent_2);

        //INICIO 36. Versión del UBL
        org.w3c.dom.Element cbcUBLVersionID = document.createElement("cbc:UBLVersionID");
        cbcUBLVersionID.appendChild(document.createTextNode(ubl));
        invoice.appendChild(cbcUBLVersionID);
        //FIN 36

        //INICIO 37. Versión de la estructura del documento
        org.w3c.dom.Element cbcCustomizationID = document.createElement("cbc:CustomizationID");
        cbcCustomizationID.appendChild(document.createTextNode(vDocomento));
        invoice.appendChild(cbcCustomizationID);
        //FIN 37

        //INICIO 8. Numeración, conformada por serie y número correlativo
        org.w3c.dom.Element cbcID_3 = document.createElement("cbc:ID");
        cbcID_3.appendChild(document.createTextNode(numeracion));
        invoice.appendChild(cbcID_3);
        //FIN 8

        //INICIO 1. Fecha de emisión. Obligatorio.
        org.w3c.dom.Element cbcIssueDate = document.createElement("cbc:IssueDate");
        cbcIssueDate.appendChild(document.createTextNode(dccFechaEmision));
        invoice.appendChild(cbcIssueDate);
        //FIN 1

        //INICIO 7. Tipo de documento (Factura)
        org.w3c.dom.Element cbcInvoiceTypeCode = document.createElement("cbc:InvoiceTypeCode");
        cbcInvoiceTypeCode.appendChild(document.createTextNode(tdcCodigo));
        invoice.appendChild(cbcInvoiceTypeCode);
        //FIN 7

        //INICIO 28. Tipo de moneda en la cual se emite la factura electrónica
        org.w3c.dom.Element cbcDocumentCurrencyCode = document.createElement("cbc:DocumentCurrencyCode");
        cbcDocumentCurrencyCode.appendChild(document.createTextNode(monCodigo));
        invoice.appendChild(cbcDocumentCurrencyCode);
        //FIN 28

        //
        org.w3c.dom.Element cacSignature = document.createElement("cac:Signature");
        invoice.appendChild(cacSignature);

        org.w3c.dom.Element cbcID_4 = document.createElement("cbc:ID");
        cbcID_4.appendChild(document.createTextNode(pe.modelo.Configuracion.nombreFirma));
        cacSignature.appendChild(cbcID_4);

        org.w3c.dom.Element cacSignatoryParty = document.createElement("cac:SignatoryParty");
        cacSignature.appendChild(cacSignatoryParty);

        org.w3c.dom.Element cacPartyIdentification = document.createElement("cac:PartyIdentification");
        cacSignatoryParty.appendChild(cacPartyIdentification);

        org.w3c.dom.Element cbcID_5 = document.createElement("cbc:ID");
        cbcID_5.appendChild(document.createTextNode(empRuc));
        cacPartyIdentification.appendChild(cbcID_5);

        org.w3c.dom.Element cacPartyName = document.createElement("cac:PartyName");
        cacSignatoryParty.appendChild(cacPartyName);

        org.w3c.dom.Element cbcName = document.createElement("cbc:Name");
        cbcName.appendChild(document.createCDATASection(empRazonSocial));
        cacPartyName.appendChild(cbcName);

        org.w3c.dom.Element cacDigitalSignatureAttachment = document.createElement("cac:DigitalSignatureAttachment");
        cacSignature.appendChild(cacDigitalSignatureAttachment);

        org.w3c.dom.Element cacExternalReference = document.createElement("cac:ExternalReference");
        cacDigitalSignatureAttachment.appendChild(cacExternalReference);

        org.w3c.dom.Element cbcURI = document.createElement("cbc:URI");
        cbcURI.appendChild(document.createTextNode("#" + pe.modelo.Configuracion.nombreFirma));
        cacExternalReference.appendChild(cbcURI);
        //

        //INICIO 6. Número de RUC. Obligatorio.
        org.w3c.dom.Element cacAccountingSupplierParty = document.createElement("cac:AccountingSupplierParty");
        invoice.appendChild(cacAccountingSupplierParty);

        org.w3c.dom.Element cbcCustomerAssignedAccountID = document.createElement("cbc:CustomerAssignedAccountID");
        cbcCustomerAssignedAccountID.appendChild(document.createTextNode(empRuc));
        cacAccountingSupplierParty.appendChild(cbcCustomerAssignedAccountID);

        org.w3c.dom.Element cbcAdditionalAccountID = document.createElement("cbc:AdditionalAccountID");
        cbcAdditionalAccountID.appendChild(document.createTextNode(empTipoEntidad));
        cacAccountingSupplierParty.appendChild(cbcAdditionalAccountID);
        //INICIO 3. Apellidos y nombres o denominación o razón social
        org.w3c.dom.Element cacParty = document.createElement("cac:Party");
        cacAccountingSupplierParty.appendChild(cacParty);

        org.w3c.dom.Element cacPartyName_2 = document.createElement("cac:PartyName");
        cacParty.appendChild(cacPartyName_2);

        org.w3c.dom.Element cbcName_2 = document.createElement("cbc:Name");
        cbcName_2.appendChild(document.createCDATASection(empRazonSocial));
        cacPartyName_2.appendChild(cbcName_2);

        //INICIO 5. Domicilio fiscal
        org.w3c.dom.Element cacPostalAddress = document.createElement("cac:PostalAddress");
        cacParty.appendChild(cacPostalAddress);

        org.w3c.dom.Element cbcID_6 = document.createElement("cbc:ID");
        cbcID_6.appendChild(document.createTextNode(ubiCodigo));
        cacPostalAddress.appendChild(cbcID_6);

        org.w3c.dom.Element cbcStreetName = document.createElement("cbc:StreetName");
        cbcStreetName.appendChild(document.createTextNode(empDireccion));
        cacPostalAddress.appendChild(cbcStreetName);

        org.w3c.dom.Element cbcCitySubdivisionName = document.createElement("cbc:CitySubdivisionName");
        cbcCitySubdivisionName.appendChild(document.createTextNode(empZona));
        cacPostalAddress.appendChild(cbcCitySubdivisionName);

        org.w3c.dom.Element cbcCityName = document.createElement("cbc:CityName");
        cbcCityName.appendChild(document.createTextNode(empDepartamento));
        cacPostalAddress.appendChild(cbcCityName);

        org.w3c.dom.Element cbcCountrySubentity = document.createElement("cbc:CountrySubentity");
        cbcCountrySubentity.appendChild(document.createTextNode(empProvincia));
        cacPostalAddress.appendChild(cbcCountrySubentity);

        org.w3c.dom.Element cbcDistrict = document.createElement("cbc:District");
        cbcDistrict.appendChild(document.createTextNode(empDistrito));
        cacPostalAddress.appendChild(cbcDistrict);

        org.w3c.dom.Element cacCountry = document.createElement("cac:Country");
        cacPostalAddress.appendChild(cacCountry);

        org.w3c.dom.Element cbcIdentificationCode = document.createElement("cbc:IdentificationCode");
        cbcIdentificationCode.appendChild(document.createTextNode(empCodigoPais));
        cacCountry.appendChild(cbcIdentificationCode);
        //FIN 5

        org.w3c.dom.Element cacPartyLegalEntity = document.createElement("cac:PartyLegalEntity");
        cacParty.appendChild(cacPartyLegalEntity);

        org.w3c.dom.Element cbcRegistrationName = document.createElement("cbc:RegistrationName");
        cbcRegistrationName.appendChild(document.createCDATASection(empRazonSocial));
        cacPartyLegalEntity.appendChild(cbcRegistrationName);
        //FIN 3
        //FIN 6
        //INICIO 10. Apellidos y nombres o denominación o razón social del adquirente o usuario
        org.w3c.dom.Element cacAccountingCustomerParty = document.createElement("cac:AccountingCustomerParty");
        invoice.appendChild(cacAccountingCustomerParty);

        //INICIO 9. Tipo y número de documento de identidad del adquirente o usuario
        org.w3c.dom.Element cbcCustomerAssignedAccountID_2 = document.createElement("cbc:CustomerAssignedAccountID");
        cbcCustomerAssignedAccountID_2.appendChild(document.createTextNode(entDocumento));
        cacAccountingCustomerParty.appendChild(cbcCustomerAssignedAccountID_2);

        org.w3c.dom.Element cbcAdditionalAccountID_2 = document.createElement("cbc:AdditionalAccountID");
        cbcAdditionalAccountID_2.appendChild(document.createTextNode(tdeCodigo));
        cacAccountingCustomerParty.appendChild(cbcAdditionalAccountID_2);
        //FIN 9

        org.w3c.dom.Element cacParty_2 = document.createElement("cac:Party");
        cacAccountingCustomerParty.appendChild(cacParty_2);

        org.w3c.dom.Element cacPartyLegalEntity_2 = document.createElement("cac:PartyLegalEntity");
        cacParty_2.appendChild(cacPartyLegalEntity_2);

        org.w3c.dom.Element cbcRegistrationName_2 = document.createElement("cbc:RegistrationName");
        cbcRegistrationName_2.appendChild(document.createCDATASection(entNombre));
        cacPartyLegalEntity_2.appendChild(cbcRegistrationName_2);
        //FIN 10
        //22. Sumatoria IGV
        //23. Sumatoria ISC
        //24. Sumatoria otros tributos
        org.w3c.dom.Element cacTaxTotal = document.createElement("cac:TaxTotal");
        invoice.appendChild(cacTaxTotal);

        org.w3c.dom.Element cbcTaxAmount = document.createElement("cbc:TaxAmount");
        cbcTaxAmount.setAttribute("currencyID", monCodigo);
        cbcTaxAmount.appendChild(document.createTextNode(dccTotalIgv));
        cacTaxTotal.appendChild(cbcTaxAmount);

        org.w3c.dom.Element cacTaxSubtotal = document.createElement("cac:TaxSubtotal");
        cacTaxTotal.appendChild(cacTaxSubtotal);

        org.w3c.dom.Element cbcTaxAmount_1 = document.createElement("cbc:TaxAmount");
        cbcTaxAmount_1.setAttribute("currencyID", monCodigo);
        cbcTaxAmount_1.appendChild(document.createTextNode(dccTotalIgv));
        cacTaxSubtotal.appendChild(cbcTaxAmount_1);

        org.w3c.dom.Element cacTaxCategory = document.createElement("cac:TaxCategory");
        cacTaxSubtotal.appendChild(cacTaxCategory);

        org.w3c.dom.Element cacTaxScheme = document.createElement("cac:TaxScheme");
        cacTaxCategory.appendChild(cacTaxScheme);

        org.w3c.dom.Element cbcID_7 = document.createElement("cbc:ID");
        cbcID_7.appendChild(document.createTextNode("1000"));
        cacTaxScheme.appendChild(cbcID_7);

        org.w3c.dom.Element cbcName_3 = document.createElement("cbc:Name");
        cbcName_3.appendChild(document.createTextNode("IGV"));
        cacTaxScheme.appendChild(cbcName_3);

        org.w3c.dom.Element cbcTaxTypeCode = document.createElement("cbc:TaxTypeCode");
        cbcTaxTypeCode.appendChild(document.createTextNode("VAT"));
        cacTaxScheme.appendChild(cbcTaxTypeCode);
        //FIN

        //INICIO 27. Importe total de la venta, de la cesión en uso o del servicio prestado
        org.w3c.dom.Element cacLegalMonetaryTotal = document.createElement("cac:LegalMonetaryTotal");
        invoice.appendChild(cacLegalMonetaryTotal);

        org.w3c.dom.Element cbcPayableAmount_2 = document.createElement("cbc:PayableAmount");
        cbcPayableAmount_2.setAttribute("currencyID", monCodigo);
        cbcPayableAmount_2.appendChild(document.createTextNode(dccTotalVenta));
        cacLegalMonetaryTotal.appendChild(cbcPayableAmount_2);
        //FIN 27
        Set<DocumentoVentaDetalle> documentoVentaDetalles = documentoVenta.getDocumentoVentaDetalles();
        Integer cont = 0;
        for (DocumentoVentaDetalle documentoVentaDetalle : documentoVentaDetalles) {
            invoice.appendChild(contruirDetalleFactura(documentoVentaDetalle, monCodigo, Integer.toString(cont + 1)));
        }
    }

    private org.w3c.dom.Element contruirDetalleFactura(DocumentoVentaDetalle documentoVentaDetalle, String monCodigo, String nroDetalle) {
        //Cantidad
        String dcdCantidad = Double.toString(pe.modelo.Configuracion.redondearDecimales(documentoVentaDetalle.getCantidad(), 2));

        //Venta total
        String dcdVenta = Double.toString(pe.modelo.Configuracion.redondearDecimales(documentoVentaDetalle.getTotal(), 2));

        //Valor Unitario
        String dcdValorUnitario = Double.toString(pe.modelo.Configuracion.redondearDecimales(documentoVentaDetalle.getValorUnitario(), 2));

        //Precio Unitario
        String dcdPrecioUnitario = Double.toString(pe.modelo.Configuracion.redondearDecimales(documentoVentaDetalle.getPrecioUnitario(), 2));
        String tipCodigo = documentoVentaDetalle.getCodigoPrecio();

        //Gratuito
        Boolean dcdIndicadorGrat = documentoVentaDetalle.isGratuito();// detalle.getBoolean("dcdIndicadorGrat");
        String dcdPrecioGratuito = Double.toString(pe.modelo.Configuracion.redondearDecimales(documentoVentaDetalle.getPrecioUnitario(), 2));
        String tipCodigoGratuito = documentoVentaDetalle.getCodigoPrecio();

        //Descuento
        String dcdIndicadorDesc = Boolean.toString(documentoVentaDetalle.isDescuento());
        String dcdValorDesc = Double.toString(pe.modelo.Configuracion.redondearDecimales(documentoVentaDetalle.getTotalDescuento(), 2));
        //IGV
        String afectacion = Double.toString(pe.modelo.Configuracion.redondearDecimales(documentoVentaDetalle.getTotalIgv(), 2));
        String codigoAfectacion = documentoVentaDetalle.getCodigoIgv();

        //Producto
        String proId = Long.toString(documentoVentaDetalle.getProducto().getId());
        String proNombre = documentoVentaDetalle.getProducto().getNombre();
        String uniCodigo = documentoVentaDetalle.getUnidad().getCodigo();

        org.w3c.dom.Element cacInvoiceLine = document.createElement("cac:InvoiceLine");
        //invoice.appendChild(cacInvoiceLine);

        org.w3c.dom.Element cbcID_8 = document.createElement("cbc:ID");
        cbcID_8.appendChild(document.createTextNode(nroDetalle));
        cacInvoiceLine.appendChild(cbcID_8);

        org.w3c.dom.Element cbcInvoicedQuantity = document.createElement("cbc:InvoicedQuantity");
        cbcInvoicedQuantity.setAttribute("unitCode", uniCodigo);
        cbcInvoicedQuantity.appendChild(document.createTextNode(dcdCantidad));
        cacInvoiceLine.appendChild(cbcInvoicedQuantity);

        org.w3c.dom.Element cbcLineExtensionAmount = document.createElement("cbc:LineExtensionAmount");
        cbcLineExtensionAmount.setAttribute("currencyID", monCodigo);
        cbcLineExtensionAmount.appendChild(document.createTextNode(dcdVenta));
        cacInvoiceLine.appendChild(cbcLineExtensionAmount);

        org.w3c.dom.Element cacPricingReference = document.createElement("cac:PricingReference");
        cacInvoiceLine.appendChild(cacPricingReference);

        org.w3c.dom.Element cacAlternativeConditionPrice = document.createElement("cac:AlternativeConditionPrice");
        cacPricingReference.appendChild(cacAlternativeConditionPrice);

        org.w3c.dom.Element cbcPriceAmount_2 = document.createElement("cbc:PriceAmount");
        cbcPriceAmount_2.setAttribute("currencyID", monCodigo);
        cbcPriceAmount_2.appendChild(document.createTextNode(dcdPrecioUnitario));
        cacAlternativeConditionPrice.appendChild(cbcPriceAmount_2);

        org.w3c.dom.Element cbcPriceTypeCode = document.createElement("cbc:PriceTypeCode");
        cbcPriceTypeCode.appendChild(document.createTextNode(tipCodigo));
        cacAlternativeConditionPrice.appendChild(cbcPriceTypeCode);

        if (dcdIndicadorGrat) {
            org.w3c.dom.Element cacAlternativeConditionPrice_2 = document.createElement("cac:AlternativeConditionPrice");
            cacPricingReference.appendChild(cacAlternativeConditionPrice_2);

            org.w3c.dom.Element cbcPriceAmount_3 = document.createElement("cbc:PriceAmount");
            cbcPriceAmount_3.setAttribute("currencyID", monCodigo);
            cbcPriceAmount_3.appendChild(document.createTextNode(dcdPrecioGratuito));
            cacAlternativeConditionPrice_2.appendChild(cbcPriceAmount_3);

            org.w3c.dom.Element cbcPriceTypeCode_2 = document.createElement("cbc:PriceTypeCode");
            cbcPriceTypeCode_2.appendChild(document.createTextNode(tipCodigoGratuito));
            cacAlternativeConditionPrice_2.appendChild(cbcPriceTypeCode_2);
        }

        org.w3c.dom.Element cacAllowanceCharge = document.createElement("cac:AllowanceCharge");
        cacInvoiceLine.appendChild(cacAllowanceCharge);

        org.w3c.dom.Element cbcChargeIndicator = document.createElement("cbc:ChargeIndicator");
        cbcChargeIndicator.appendChild(document.createTextNode(dcdIndicadorDesc));
        cacAllowanceCharge.appendChild(cbcChargeIndicator);

        org.w3c.dom.Element cbcAmount = document.createElement("cbc:Amount");
        cbcAmount.setAttribute("currencyID", monCodigo);
        cbcAmount.appendChild(document.createTextNode(dcdValorDesc));
        cacAllowanceCharge.appendChild(cbcAmount);

        org.w3c.dom.Element cacTaxTotal_2 = document.createElement("cac:TaxTotal");
        cacInvoiceLine.appendChild(cacTaxTotal_2);

        org.w3c.dom.Element cbcTaxAmount_2 = document.createElement("cbc:TaxAmount");
        cbcTaxAmount_2.setAttribute("currencyID", monCodigo);
        cbcTaxAmount_2.appendChild(document.createTextNode(afectacion));
        cacTaxTotal_2.appendChild(cbcTaxAmount_2);

        org.w3c.dom.Element cacTaxSubtotal_2 = document.createElement("cac:TaxSubtotal");
        cacTaxTotal_2.appendChild(cacTaxSubtotal_2);

        org.w3c.dom.Element cbcTaxAmount_3 = document.createElement("cbc:TaxAmount");
        cbcTaxAmount_3.setAttribute("currencyID", monCodigo);
        cbcTaxAmount_3.appendChild(document.createTextNode(afectacion));
        cacTaxSubtotal_2.appendChild(cbcTaxAmount_3);

        org.w3c.dom.Element cacTaxCategory_2 = document.createElement("cac:TaxCategory");
        cacTaxSubtotal_2.appendChild(cacTaxCategory_2);

        org.w3c.dom.Element cbcTaxExemptionReasonCode = document.createElement("cbc:TaxExemptionReasonCode");
        cbcTaxExemptionReasonCode.appendChild(document.createTextNode(codigoAfectacion));
        cacTaxCategory_2.appendChild(cbcTaxExemptionReasonCode);

        org.w3c.dom.Element cacTaxScheme_2 = document.createElement("cac:TaxScheme");
        cacTaxCategory_2.appendChild(cacTaxScheme_2);

        org.w3c.dom.Element cbcID_9 = document.createElement("cbc:ID");
        cbcID_9.appendChild(document.createTextNode("1000"));
        cacTaxScheme_2.appendChild(cbcID_9);

        org.w3c.dom.Element cbcName_4 = document.createElement("cbc:Name");
        cbcName_4.appendChild(document.createTextNode("IGV"));
        cacTaxScheme_2.appendChild(cbcName_4);

        org.w3c.dom.Element cbcTaxTypeCode_2 = document.createElement("cbc:TaxTypeCode");
        cbcTaxTypeCode_2.appendChild(document.createTextNode("VAT"));
        cacTaxScheme_2.appendChild(cbcTaxTypeCode_2);

        org.w3c.dom.Element cacItem = document.createElement("cac:Item");
        cacInvoiceLine.appendChild(cacItem);

        org.w3c.dom.Element cbcDescription = document.createElement("cbc:Description");
        cbcDescription.appendChild(document.createCDATASection(proNombre));
        cacItem.appendChild(cbcDescription);

        org.w3c.dom.Element cacSellersItemIdentification = document.createElement("cac:SellersItemIdentification");
        cacItem.appendChild(cacSellersItemIdentification);

        org.w3c.dom.Element cbcID_10 = document.createElement("cbc:ID");
        cbcID_10.appendChild(document.createTextNode(proId));
        cacSellersItemIdentification.appendChild(cbcID_10);

        org.w3c.dom.Element cacPrice = document.createElement("cac:Price");
        cacInvoiceLine.appendChild(cacPrice);

        org.w3c.dom.Element cbcPriceAmount = document.createElement("cbc:PriceAmount");
        cbcPriceAmount.setAttribute("currencyID", monCodigo);
        cbcPriceAmount.appendChild(document.createTextNode(dcdValorUnitario));
        cacPrice.appendChild(cbcPriceAmount);
        //FIN 14
        return cacInvoiceLine;
    }

    private void generarXml() {
        try {
            TransformerFactory factoria = TransformerFactory.newInstance();
            Transformer transformer = factoria.newTransformer();

            transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            document.getDocumentElement().normalize();

            Source source = new DOMSource(document);
            File file = new File(fileName + ".xml");
            FileWriter fw = new FileWriter(file);
            PrintWriter pw = new PrintWriter(fw);
            Result result = new StreamResult(pw);

            transformer.transform(source, result);
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(Factura.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Factura.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(Factura.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void firmarXml() {
        try {
            XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");
            Reference ref = fac.newReference("", fac.newDigestMethod(DigestMethod.SHA1, null),
                    Collections.singletonList(fac.newTransform(Transform.ENVELOPED, (TransformParameterSpec) null)), null, null);
            SignedInfo si = fac.newSignedInfo(fac.newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE, (C14NMethodParameterSpec) null),
                    fac.newSignatureMethod(SignatureMethod.RSA_SHA1, null),
                    Collections.singletonList(ref));
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(new FileInputStream(pe.modelo.Configuracion.rutaCertificado + pe.modelo.Configuracion.nombreCertificado), pe.modelo.Configuracion.claveCertificado.toCharArray());
            KeyStore.PrivateKeyEntry keyEntry = (KeyStore.PrivateKeyEntry) ks.getEntry(pe.modelo.Configuracion.usuarioLlave, new KeyStore.PasswordProtection(pe.modelo.Configuracion.claveLlave.toCharArray()));
            X509Certificate cert = (X509Certificate) keyEntry.getCertificate();

            KeyInfoFactory kif = fac.getKeyInfoFactory();
            List x509Content = new ArrayList();
            x509Content.add(cert.getSubjectX500Principal().getName());
            x509Content.add(cert);
            X509Data xd = kif.newX509Data(x509Content);
            KeyInfo ki = kif.newKeyInfo(Collections.singletonList(xd));

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            Document doc = dbf.newDocumentBuilder().parse(new FileInputStream(fileName + ".xml"));

            DOMSignContext dsc = new DOMSignContext(keyEntry.getPrivateKey(), doc.getElementsByTagName("ext:ExtensionContent").item(1));

            dsc.setDefaultNamespacePrefix("ds");

            XMLSignature signature = fac.newXMLSignature(si, ki, null, pe.modelo.Configuracion.nombreFirma, null);

            signature.sign(dsc);

            OutputStream os = new FileOutputStream(fileName + ".xml");
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer trans = tf.newTransformer();
            trans.transform(new DOMSource(doc), new StreamResult(os));
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Factura.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidAlgorithmParameterException ex) {
            Logger.getLogger(Factura.class.getName()).log(Level.SEVERE, null, ex);
        } catch (KeyStoreException ex) {
            Logger.getLogger(Factura.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Factura.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CertificateException ex) {
            Logger.getLogger(Factura.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnrecoverableEntryException ex) {
            Logger.getLogger(Factura.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Factura.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MarshalException ex) {
            Logger.getLogger(Factura.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XMLSignatureException ex) {
            Logger.getLogger(Factura.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(Factura.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(Factura.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(Factura.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void generarZip() {
        try {
            File f = new File(fileName + ".zip");
            if (f.exists() && !f.isDirectory()) {
                f.delete();
            }
            ZipFile zipFile = new ZipFile(fileName + ".zip");

            ArrayList filesToAdd = new ArrayList();
            filesToAdd.add(new File(fileName + ".xml"));

            //addFileToZip// .add(new File("dummy/"));
            ZipParameters parameters = new ZipParameters();
            parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE); // set compression method to deflate compression

            parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
            zipFile.addFiles(filesToAdd, parameters);

            //zipFile.addFolder("dummy", parameters);
        } catch (ZipException e) {
            e.printStackTrace();
        }

    }

    private void inicializarServicioWeb() {
        service = new BillService_Service();
        port = service.getBillServicePort();
    }

    private void agregarHeaderSecurity() {
        BindingProvider bindingProvider = (BindingProvider) port;
        @SuppressWarnings("request-headers")
        List<Handler> handlerChain = new ArrayList<Handler>();
        handlerChain.add(new WSSecurityHeaderSOAPHandler(pe.modelo.Configuracion.usuarioSol, pe.modelo.Configuracion.claveSol));
        bindingProvider.getBinding().setHandlerChain(handlerChain);
    }

    private void sendBill() {
        try {
            //documentoDAO documentoDAO = new documentoDAO();
            FileDataSource file = new FileDataSource(fileName + ".zip");
            javax.activation.DataHandler contentFile = new javax.activation.DataHandler(file);
            byte[] retorno = port.sendBill(contentFile.getName(), contentFile);
            String archivo = pe.modelo.Configuracion.rutaCDRsFacturas + "R-" + contentFile.getName();
            FileOutputStream retornofactura = new FileOutputStream(archivo);

            retornofactura.write(retorno);
            ZipInputStream zis = new ZipInputStream(new FileInputStream(archivo));
            ZipEntry entrada;
            while (null != (entrada = zis.getNextEntry())) {
                //System.out.println(entrada.getName());
                OutputStream fos = new FileOutputStream(pe.modelo.Configuracion.rutaCDRsFacturas + entrada.getName());
                //FileOutputStream fos = new FileOutputStream(entrada.getName());
                int leido;
                byte[] buffer = new byte[1024];
                while (0 < (leido = zis.read(buffer))) {
                    fos.write(buffer, 0, leido);
                }
                fos.close();
                zis.closeEntry();
            }
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            String xmlRsp = pe.modelo.Configuracion.rutaCDRsFacturas + "R-" + contentFile.getName().replace(".zip", ".xml");
            String xmlEnv = pe.modelo.Configuracion.rutaFacturas + contentFile.getName().replace(".zip", ".xml");
            //System.out.println("xmlRsp:" + xmlRsp);
            Document document = builder.parse(new File(xmlRsp));
            document.getDocumentElement().normalize();
            //org.w3c.dom.Element node = document.getElementById("ResponseCode");
            String ResponseCode = document.getElementsByTagName("cbc:ResponseCode").item(0).getTextContent();
            documentoVenta.setCodigoSunat(ResponseCode);
            //System.out.println("RSP-CDR:" + node.getTextContent());
            //documentoDAO.actualizarRspSunat(dccIdGeneral, ResponseCode);

            Document document2 = builder.parse(new File(xmlEnv));
            document2.getDocumentElement().normalize();
            String dccDigestValue = document2.getElementsByTagName("ds:DigestValue").item(0).getTextContent();
            documentoVenta.setCodigoHash(dccDigestValue);
            //documentoDAO.actualizarHash(dccIdGeneral, dccDigestValue);
            //5:Correcto
            //6:Incorrecto
            EstadoDocumentoVenta estadoDocumentoVenta = new EstadoDocumentoVenta();
            if (ResponseCode.equals("0")) {
                estadoDocumentoVenta.setCodigo("VAL");
                documentoVenta.setEstadoDocumentoVenta(estadoDocumentoVenta);
                //documentoDAO.actualizarEnvioSunat(dccIdGeneral, "5");
            } else {
                estadoDocumentoVenta.setCodigo("REC");
                documentoVenta.setEstadoDocumentoVenta(estadoDocumentoVenta);
                //documentoDAO.actualizarEnvioSunat(dccIdGeneral, "6");
            }
        } catch (javax.xml.ws.soap.SOAPFaultException ex) {
            String error = ex.getFault().getFaultCodeAsQName().getLocalPart();
            //System.out.println(error);
            documentoVenta.setCodigoSunat(error);
            //documentoDAO documentoDAO = new documentoDAO();
            //documentoDAO.actualizarRspSunat(dccIdGeneral, error);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Factura.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Factura.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Factura.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(Factura.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
