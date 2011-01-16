package org.ftm.xml;

import com.sun.istack.internal.Nullable;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;

/**
 * @author <font size=-1 color="#a3a3a3">Johnny Hujol</font>
 * @since 1/16/11
 */
public final class XPathReader {

    private final Document xmlDoc;
    private final XPath xPath;

    public XPathReader(String xmlContent) throws Exception {
        final ByteArrayInputStream bis = new ByteArrayInputStream(xmlContent.getBytes("UTF-8"));
        xmlDoc = DocumentBuilderFactory.
            newInstance().newDocumentBuilder().
            parse(bis);

        xPath = XPathFactory.newInstance().newXPath();
    }

    /**
     * @param xpathExpression an XPath expression as defined in {@link javax.xml.xpath}
     * @param node            the node to start with; if it is null then it starts from the root node of the document.
     * @param returnType      one of {@link XPathConstants}
     * @param <T>             need to match what {@link XPathConstants} map to.
     * @return the value typed by {@link T}
     * @throws XPathExpressionException
     */
    public <T> T evaluate(String xpathExpression, @Nullable Node node, QName returnType) throws XPathExpressionException {
        //noinspection unchecked
        return (T) xPath.evaluate(xpathExpression, null == node ? xmlDoc : node, returnType);
    }

    public <T> T evaluate(String xpathExpression, QName returnType) throws XPathExpressionException {
        return (T) evaluate(xpathExpression, xmlDoc, returnType);
    }

    public String evaluate(String exp, Node item) throws XPathExpressionException {
        return evaluate(exp, item, XPathConstants.STRING);
    }

    public String evaluate(String exp) throws XPathExpressionException {
        return evaluate(exp, xmlDoc, XPathConstants.STRING);
    }
}
