//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.07.04 at 06:08:42 PM IST 
//


package utilities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Document" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="ApplicationID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="DocumentClass" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="Indices">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="IndexField" maxOccurs="unbounded">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="idxName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="idxValue" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
		"document"
})
@XmlRootElement(name = "Documents")
public class Documents implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@XmlElement(name = "Document", required = true)
	protected List<Documents.Document> document;

	public void setDocument(List<Documents.Document> document) {
		this.document = document;
	}


	/**
	 * Gets the value of the document property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list,
	 * not a snapshot. Therefore any modification you make to the
	 * returned list will be present inside the JAXB object.
	 * This is why there is not a <CODE>set</CODE> method for the document property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * <pre>
	 *    getDocument().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link Documents.Document }
	 * 
	 * 
	 */
	public List<Documents.Document> getDocument() {
		if (document == null) {
			document = new ArrayList<Documents.Document>();
		}
		return this.document;
	}


	/**
	 * <p>Java class for anonymous complex type.
	 * 
	 * <p>The following schema fragment specifies the expected content contained within this class.
	 * 
	 * <pre>
	 * &lt;complexType>
	 *   &lt;complexContent>
	 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
	 *       &lt;sequence>
	 *         &lt;element name="ApplicationID" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *         &lt;element name="DocumentClass" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *         &lt;element name="Indices">
	 *           &lt;complexType>
	 *             &lt;complexContent>
	 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
	 *                 &lt;sequence>
	 *                   &lt;element name="IndexField" maxOccurs="unbounded">
	 *                     &lt;complexType>
	 *                       &lt;complexContent>
	 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
	 *                           &lt;sequence>
	 *                             &lt;element name="idxName" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                             &lt;element name="idxValue" type="{http://www.w3.org/2001/XMLSchema}int"/>
	 *                           &lt;/sequence>
	 *                         &lt;/restriction>
	 *                       &lt;/complexContent>
	 *                     &lt;/complexType>
	 *                   &lt;/element>
	 *                 &lt;/sequence>
	 *               &lt;/restriction>
	 *             &lt;/complexContent>
	 *           &lt;/complexType>
	 *         &lt;/element>
	 *       &lt;/sequence>
	 *     &lt;/restriction>
	 *   &lt;/complexContent>
	 * &lt;/complexType>
	 * </pre>
	 * 
	 * 
	 */
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = {
			"applicationID",
			"documentClass",
			"indices"
	})
	@XmlRootElement(name = "Document")
	public static class Document implements Serializable{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		@XmlElement(name = "ApplicationID", required = true)
		protected String applicationID;
		@XmlElement(name = "DocumentClass", required = true)
		protected String documentClass;
		@XmlElement(name = "Indices", required = true)
		protected Documents.Document.Indices indices;

		/**
		 * Gets the value of the applicationID property.
		 * 
		 * @return
		 *     possible object is
		 *     {@link String }
		 *     
		 */
		public String getApplicationID() {
			return applicationID;
		}

		/**
		 * Sets the value of the applicationID property.
		 * 
		 * @param value
		 *     allowed object is
		 *     {@link String }
		 *     
		 */
		public void setApplicationID(String value) {
			this.applicationID = value;
		}

		/**
		 * Gets the value of the documentClass property.
		 * 
		 * @return
		 *     possible object is
		 *     {@link String }
		 *     
		 */
		public String getDocumentClass() {
			return documentClass;
		}

		/**
		 * Sets the value of the documentClass property.
		 * 
		 * @param value
		 *     allowed object is
		 *     {@link String }
		 *     
		 */
		public void setDocumentClass(String value) {
			this.documentClass = value;
		}

		/**
		 * Gets the value of the indices property.
		 * 
		 * @return
		 *     possible object is
		 *     {@link Documents.Document.Indices }
		 *     
		 */
		public Documents.Document.Indices getIndices() {
			return indices;
		}

		/**
		 * Sets the value of the indices property.
		 * 
		 * @param value
		 *     allowed object is
		 *     {@link Documents.Document.Indices }
		 *     
		 */
		public void setIndices(Documents.Document.Indices value) {
			this.indices = value;
		}


		/**
		 * <p>Java class for anonymous complex type.
		 * 
		 * <p>The following schema fragment specifies the expected content contained within this class.
		 * 
		 * <pre>
		 * &lt;complexType>
		 *   &lt;complexContent>
		 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
		 *       &lt;sequence>
		 *         &lt;element name="IndexField" maxOccurs="unbounded">
		 *           &lt;complexType>
		 *             &lt;complexContent>
		 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
		 *                 &lt;sequence>
		 *                   &lt;element name="idxName" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *                   &lt;element name="idxValue" type="{http://www.w3.org/2001/XMLSchema}int"/>
		 *                 &lt;/sequence>
		 *               &lt;/restriction>
		 *             &lt;/complexContent>
		 *           &lt;/complexType>
		 *         &lt;/element>
		 *       &lt;/sequence>
		 *     &lt;/restriction>
		 *   &lt;/complexContent>
		 * &lt;/complexType>
		 * </pre>
		 * 
		 * 
		 */
		@XmlAccessorType(XmlAccessType.FIELD)
		@XmlType(name = "", propOrder = {
				"indexField"
		})
		public static class Indices implements Serializable{

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			@XmlElement(name = "IndexField", required = true)
			protected List<Documents.Document.Indices.IndexField> indexField;

			/**
			 * Gets the value of the indexField property.
			 * 
			 * <p>
			 * This accessor method returns a reference to the live list,
			 * not a snapshot. Therefore any modification you make to the
			 * returned list will be present inside the JAXB object.
			 * This is why there is not a <CODE>set</CODE> method for the indexField property.
			 * 
			 * <p>
			 * For example, to add a new item, do as follows:
			 * <pre>
			 *    getIndexField().add(newItem);
			 * </pre>
			 * 
			 * 
			 * <p>
			 * Objects of the following type(s) are allowed in the list
			 * {@link Documents.Document.Indices.IndexField }
			 * 
			 * 
			 */
			public List<Documents.Document.Indices.IndexField> getIndexField() {
				if (indexField == null) {
					indexField = new ArrayList<>();
				}
				return this.indexField;
			}

			public void setIndexField(List<Documents.Document.Indices.IndexField> indexField) {
				this.indexField = indexField;
			}

			/**
			 * <p>Java class for anonymous complex type.
			 * 
			 * <p>The following schema fragment specifies the expected content contained within this class.
			 * 
			 * <pre>
			 * &lt;complexType>
			 *   &lt;complexContent>
			 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
			 *       &lt;sequence>
			 *         &lt;element name="idxName" type="{http://www.w3.org/2001/XMLSchema}string"/>
			 *         &lt;element name="idxValue" type="{http://www.w3.org/2001/XMLSchema}int"/>
			 *       &lt;/sequence>
			 *     &lt;/restriction>
			 *   &lt;/complexContent>
			 * &lt;/complexType>
			 * </pre>
			 * 
			 * 
			 */
			@XmlAccessorType(XmlAccessType.FIELD)
			@XmlType(name = "", propOrder = {
					"idxName",
					"idxValue"
			})
			public static class IndexField implements Serializable {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;
				@XmlElement(required = true)
				protected String idxName;
				protected String idxValue;

				/**
				 * Gets the value of the idxName property.
				 * 
				 * @return
				 *     possible object is
				 *     {@link String }
				 *     
				 */
				public String getIdxName() {
					return idxName;
				}

				/**
				 * Sets the value of the idxName property.
				 * 
				 * @param value
				 *     allowed object is
				 *     {@link String }
				 *     
				 */
				public void setIdxName(String value) {
					this.idxName = value;
				}

				/**
				 * Gets the value of the idxValue property.
				 * 
				 */
				public String getIdxValue() {
					return idxValue;
				}

				/**
				 * Sets the value of the idxValue property.
				 * 
				 */
				public void setIdxValue(String value) {
					this.idxValue = value;
				}

				@Override
				public String toString() {
					return "idxName: " + idxName + " idxValue " + idxValue;
				}

				@Override
				public int hashCode() {
					final int prime = 31;
					int result = 1;
					result = prime * result + ((idxName == null) ? 0 : idxName.hashCode());
					return result;
				}

				@Override
				public boolean equals(Object obj) {
					if (this == obj)
						return true;
					if (obj == null)
						return false;
					if (getClass() != obj.getClass())
						return false;
					IndexField other = (IndexField) obj;
					if (idxName == null) {
						if (other.idxName != null)
							return false;
					} else if (!idxName.equals(other.idxName))
						return false;
					return true;
				}
				
				
				
				

			}
			
			@Override
			public String toString() {
				return "indicesList: " + indexField; 
			}

		}

		@Override
		public String toString() {
			return "appId: " + applicationID + " DocClass " + documentClass + " indices: " + indices;
		}
	}
}