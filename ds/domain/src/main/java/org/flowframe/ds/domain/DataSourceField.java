package org.flowframe.ds.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.metamodel.Attribute.PersistentAttributeType;
import javax.persistence.metamodel.Type.PersistenceType;

import org.flowframe.kernel.common.utils.StringUtil;
import org.flowframe.kernel.common.mdm.domain.MultitenantBaseEntity;
import org.flowframe.kernel.common.mdm.domain.metamodel.AbstractType;
import org.flowframe.kernel.common.mdm.domain.metamodel.EntityType;

/**
 * Metadata about a DataSourceField, including its type and validators.
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "ffsysdsdatasourcefield")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class DataSourceField extends MultitenantBaseEntity {
	private int ordinal = -1;
	
	@ManyToOne
	private DataSourceField parentDataSourceField;
	
	@OneToMany(mappedBy = "parentDataSourceField", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<DataSourceField> childDataSourceFields = new HashSet<DataSourceField>();
	
	@OneToMany(mappedBy = "field", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<DataSourceFieldValidator> validators = new HashSet<DataSourceFieldValidator>();
	
	@OneToMany(mappedBy = "field", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<DataSourceFieldDependenceExpression> dependenceExpressions = new HashSet<DataSourceFieldDependenceExpression>();
	
	public DataSourceField() {
	}

	public DataSourceField(Boolean primarykey, String name, DataSource parentDataSource, AbstractType dataType, String title) {
		this(name, parentDataSource, dataType, title);
		this.primaryKey = primarykey;
	}

	public DataSourceField(String name, DataSource parentDataSource, AbstractType dataType, String title) {
		setName(name);
		this.title = title;
		this.parentDataSource = parentDataSource;
		this.dataType = dataType;
		this.multiple = false;
	}
	
	public DataSourceField(String name, DataSource parentDataSource, AbstractType dataType, String title, int ordinal) {
		setName(name);
		this.title = title;
		this.parentDataSource = parentDataSource;
		this.dataType = dataType;
		this.multiple = false;
		this.ordinal = ordinal;
	}

	// ********************* Properties / Attributes ***********************

	public DataSourceField(String name, DataSource parentDataSource, DataSource dataSource, EntityType entityType, String title, PersistentAttributeType at) {
		this(name, parentDataSource, entityType, title);

		this.dataSource = dataSource;

		if (at == PersistentAttributeType.MANY_TO_ONE || at == PersistentAttributeType.ONE_TO_ONE) {
			this.multiple = false;
		} else // Collection
		{
			this.multiple = true;
		}
	}

	/**
	 * Controls whether, by default, dataBoundComponents consider this field
	 * editable.
	 * <P>
	 * Set to <code>false</code> to draw this field read-only.
	 * <P>
	 * This attribute may not effect all dataBoundComponents - the
	 * {@link com.smartgwt.client.widgets.DataBoundComponent#getCanEditFieldAttribute
	 * canEditFieldAttribute} may be set at the component level to look for a
	 * different attribute on the dataSourceField, and components allow
	 * developers to explicitly override this default (see
	 * {@link com.smartgwt.client.widgets.grid.ListGridField#getCanEdit canEdit}. {@link com.smartgwt.client.widgets.form.fields.FormItem#getCanEdit
	 * canEdit} for example).
	 * <P>
	 * Note that this setting only prevents the user from modifying the field's
	 * value through the UI; the value can still be modified programmatically,
	 * and it will still be saved in the normal way. If you wish to prevent a
	 * field from being saved, use
	 * {@link com.smartgwt.client.data.DataSourceField#getCanSave canSave}:false
	 * instead (or in addition).
	 * 
	 * @param canEdit
	 *            canEdit Default value is null
	 * @see com.smartgwt.client.data.DataSourceField#setCanFilter
	 * @see com.smartgwt.client.data.DataSourceField#setCanSave
	 * @see com.smartgwt.client.docs.ComponentBinding ComponentBinding overview
	 *      and related methods
	 */
	private Boolean canEdit = true;

	/**
	 * Dictates whether the data in this field be exported. Explicitly setting
	 * <i>canExport</i> to false overrides the setting on any component-fields,
	 * such as
	 * {@link com.smartgwt.client.widgets.grid.ListGridField#getCanExport
	 * ListGrid fields}.
	 * 
	 * @param canExport
	 *            canExport Default value is null
	 */
	private Boolean canExport = false;

	/**
	 * Should the user be able to filter data by this field? Effects whether
	 * this field will show up in dataBoundComponents with UI for filtering
	 * data.
	 * <P>
	 * 
	 * @param canFilter
	 *            canFilter Default value is null
	 * @see com.smartgwt.client.widgets.form.SearchForm#setShowFilterFieldsOnly
	 * @see com.smartgwt.client.widgets.form.SearchForm#setCanEditFieldAttribute
	 */
	private Boolean canFilter = false;

	/**
	 * Whether values in this field can be updated and saved to the dataSource.
	 * <P>
	 * If set to <code>false</code>, this field will default to being
	 * non-editable in standard editing components (
	 * {@link com.smartgwt.client.widgets.form.DynamicForm}, editable
	 * {@link com.smartgwt.client.widgets.grid.ListGrid}), but will be editable
	 * when displayed for filtering purposes only (in a
	 * {@link com.smartgwt.client.widgets.form.SearchForm} or
	 * {@link com.smartgwt.client.widgets.grid.ListGrid#getShowFilterEditor
	 * ListGrid filter editor}. If
	 * {@link com.smartgwt.client.data.DataSourceField#getCanEdit canEdit} is
	 * explicitly specified it will take precedence over this client-side
	 * behavior, but the server will still enforce the no-save policy (described
	 * below).
	 * <p>
	 * NOTE: If you are using Smart GWT Server and have specified
	 * <code>canSave: false</code> for a field in a DataSource definition (
	 * <code>.ds.xml</code> file), this is enforced on the server. This means
	 * that we will strip out any attempt to set the value of such a field
	 * before trying to process any update or add request, similar to what
	 * happens when a
	 * {@link com.smartgwt.client.docs.serverds.DataSourceField#editRequiresAuthentication
	 * field-level declarative security check} fails.
	 * 
	 * @param canSave
	 *            canSave Default value is null
	 * @see com.smartgwt.client.docs.ComponentBinding ComponentBinding overview
	 *      and related methods
	 */
	private Boolean canSave = true;

	/**
	 * When true, this field can only be used for sorting if the data is
	 * entirely client-side.
	 * 
	 * @param canSortClientOnly
	 *            canSortClientOnly Default value is false
	 */
	private Boolean canSort = true;

	/**
	 * If false, this property indicates that this field is considered
	 * "server only". This means:
	 * <ul>
	 * <li>Components cannot bind to the field; even if you explicitly add a
	 * field with the same name to your
	 * {@link com.smartgwt.client.widgets.DataBoundComponent dataBoundComponent}
	 * , it will be dropped</li>
	 * <li>If you are using Smart GWT Server, the client will never be sent a
	 * value for the field</li>
	 * <li>If you are using Smart GWT Server, then similar to
	 * {@link com.smartgwt.client.data.DataSourceField#getCanEdit canEdit}, no
	 * updates to the field are allowed from the client. If you explicitly add a
	 * value for the field to, eg, a record you are passing to
	 * {@link com.smartgwt.client.data.DataSource#updateData
	 * DataSource.updateData}, the server will strip the value out of the record
	 * before processing the update request.
	 * </ul>
	 * <code>canView:false</code> is <b>not</b> the same thing as
	 * {@link com.smartgwt.client.data.DataSourceField#getHidden hidden}. Use
	 * <code>canView:false</code> when you want to prevent the client from ever
	 * seeing a field's definition or values; use <code>hidden:true</code> if it
	 * is fine from a security perspective that a field's definition and values
	 * are sent to the browser, but the field should not by default appear in
	 * user interface elements (but could do in some cases, like a special
	 * screen for advanced users or administrators, for example).
	 * <p>
	 * Note that this property must be set explicitly to false to have an
	 * effect; a null or undefined setting is treated the same as true.
	 * <P>
	 * This property is used to implement field-level view security: failing a
	 * {@link com.smartgwt.client.docs.serverds.DataSourceField#viewRequiresAuthentication
	 * viewRequiresAuthentication},
	 * {@link com.smartgwt.client.docs.serverds.DataSourceField#viewRequiresRole
	 * viewRequiresRole} or
	 * {@link com.smartgwt.client.docs.serverds.DataSourceField#viewRequires
	 * viewRequires} test is equivalent to setting <code>canView:false</code> on
	 * the field (and, indeed, from the client's perspective, the field
	 * <em>has</em> had <code>canView:false</code> set).
	 * 
	 * @param canView
	 *            canView Default value is null
	 * @see com.smartgwt.client.docs.ComponentBinding ComponentBinding overview
	 *      and related methods
	 */
	private Boolean canView = true;

	/**
	 * If true, this property indicates that this field will hold an explicit
	 * array of child nodes for the current node. This has the same effect as
	 * specifying {@link com.smartgwt.client.data.DataSource#getChildrenField
	 * childrenField} to this field's name.
	 * 
	 * @param childrenProperty
	 *            childrenProperty Default value is false
	 * @see com.smartgwt.client.data.DataSource#setChildrenField
	 * @see com.smartgwt.client.docs.DataSourceRelations DataSourceRelations
	 *      overview and related methods
	 * 
	 */

	/**
	 * For a field that is
	 * {@link com.smartgwt.client.data.DataSourceField#getMultiple
	 * multiple:"true"}, controls the name of the XML tag used for each
	 * subelement during
	 * {@link com.smartgwt.client.data.DataSource#xmlSerialize
	 * DataSource.xmlSerialize}.
	 * <P>
	 * If unset, the default tag name is "value" for a field of simple type, and
	 * for a field of DataSource type, is the tagName or ID of the DataSource
	 * (as though <code>xmlSerialize()</code> were called on the child
	 * DataSource).
	 * 
	 * @param childTagName
	 *            . See {@link com.smartgwt.client.docs.String String}. Default
	 *            value is null
	 * @see com.smartgwt.client.docs.ComponentSchema ComponentSchema overview
	 *      and related methods
	 */

	/**
	 * Preferred display format to use for date type values within this field.
	 * If this property is set on a field displayed in a databound component
	 * such as a {@link com.smartgwt.client.widgets.form.DynamicForm} or
	 * {@link com.smartgwt.client.widgets.grid.ListGrid} it will be respected
	 * (See
	 * {@link com.smartgwt.client.widgets.form.fields.FormItem#getDateFormatter
	 * dateFormatter} and
	 * {@link com.smartgwt.client.widgets.grid.ListGridField#getDateFormatter
	 * dateFormatter}).
	 * <P>
	 * Note that this property is also honored when exporting directly to Excel
	 * spreadsheets (ie, when using XLS or XLSX/OOXML form, <b>not</b> CSV);
	 * "date" and "datetime" fields with this property set will deliver real
	 * dates and formatting information to Excel, rather than formatted strings
	 * or unformatted dates.
	 * <p>
	 * <b>Note : </b> This is an advanced setting
	 * </p>
	 * 
	 * @param dateFormatter
	 *            dateFormatter Default value is null
	 * @see com.smartgwt.client.docs.Appearance Appearance overview and related
	 *      methods
	 */

	// private DateDisplayFormatter dateFormatter;

	/**
	 * Applies only to fields of type "float" and enforces a minimum number of
	 * digits shown after the decimal point.
	 * <P>
	 * For example, a field value of 343.1, 343.104 and 343.09872677 would all
	 * be shown as 343.10 if decimalPad is 2.
	 * <P>
	 * The original unpadded value is always shown when the value is edited.
	 * 
	 * @param decimalPad
	 *            decimalPad Default value is null
	 * @see com.smartgwt.client.docs.Appearance Appearance overview and related
	 *      methods
	 */
	private Integer decimalPad = 2;

	/**
	 * Applies only to fields of type "float" and affects how many significant
	 * digits are shown.
	 * <P>
	 * For example, with decimalPrecision 3, if the field value is 343.672677,
	 * 343.673 is shown.
	 * <P>
	 * If the value is 125.2, 125.2 is shown - decimalPrecision will not cause
	 * extra zeros to be added. Use
	 * {@link com.smartgwt.client.data.DataSourceField#getDecimalPad decimalPad}
	 * for this.
	 * <P>
	 * A number if always shown with its original precision when edited.
	 * 
	 * @param decimalPrecision
	 *            decimalPrecision Default value is null
	 * @see com.smartgwt.client.docs.Appearance Appearance overview and related
	 *      methods
	 */
	private Integer decimalPrecision = 3;

	/**
	 * Whether this field should be considered a "detail" field by a
	 * {@link com.smartgwt.client.widgets.DataBoundComponent}.
	 * <P>
	 * Detail fields won't be shown by default in a DataBoundComponent where
	 * {@link com.smartgwt.client.widgets.DataBoundComponent#getShowDetailFields
	 * showDetailFields} is false. This allows for some DataBound components,
	 * like a {@link com.smartgwt.client.widgets.grid.ListGrid}, to show a
	 * summary view of records which displays only the most commonly viewed
	 * fields by default, while other DataBoundComponents, like a
	 * {@link com.smartgwt.client.widgets.viewer.DetailViewer}, show all fields
	 * by default.
	 * <P>
	 * In addition, the
	 * {@link com.smartgwt.client.widgets.form.fields.FormItem#showIf
	 * formItem.showIf} property is supported in multiple components for
	 * conditional visibility - see for example
	 * {@link com.smartgwt.client.widgets.grid.ListGridField#showIf
	 * ListGridField.showIf} and
	 * {@link com.smartgwt.client.widgets.form.fields.FormItem#showIf
	 * FormItem.showIf}).
	 * 
	 * @param detail
	 *            detail Default value is false
	 * @see com.smartgwt.client.docs.ComponentBinding ComponentBinding overview
	 *      and related methods
	 */
	private Boolean detail = false;

	/**
	 * When data values are displayed in DataBound components, by default
	 * strings will be interpreted as HTML by the browser in most cases.
	 * <P>
	 * If set, this property will be picked up by components bound to this
	 * dataSource, notifying them that any HTML characters should be escaped
	 * when displaying values for this field.
	 * 
	 * @param escapeHTML
	 *            escapeHTML Default value is null
	 * @see com.smartgwt.client.widgets.grid.ListGridField#setEscapeHTML
	 */
	private Boolean escapeHTML = false;

	/**
	 * Optional different field-title used for exports.
	 * 
	 * @param exportTitle
	 *            . See {@link com.smartgwt.client.docs.String String}. Default
	 *            value is null
	 */
	private String exportTitle;

	/**
	 * Declares that this field holds values that can be matched to values from
	 * another DataSource field, to create a relationship between records from
	 * different DataSources or even records within the same DataSource.
	 * <p>
	 * The format of <code>foreignKey</code> is
	 * <code>&lt;dataSourceId&gt;.&lt;fieldName&gt;</code>.<br>
	 * For a foreignKey within the same dataSource, you can omit the
	 * <code>dataSourceId</code> and just specify <code>&lt;fieldName&gt;</code>
	 * .<br>
	 * For example, to create a tree relationship within a DataSource:
	 * 
	 * <pre>
	 *    DataSource.create({
	 *      ID:"supplyItem",
	 *      fields : [
	 *        {name:"itemId", type:"sequence", primaryKey:true},
	 *        {name:"parentId", type:"integer", foreignKey:"itemId"},
	 *        ...
	 *      ]
	 *    });
	 * </pre>
	 * <P>
	 * <code>foreignKey</code> declarations also allow other automatic behaviors
	 * by {@link com.smartgwt.client.widgets.DataBoundComponent
	 * DataBoundComponents}, such as
	 * {@link com.smartgwt.client.widgets.grid.ListGrid#fetchRelatedData
	 * ListGrid.fetchRelatedData}.
	 * <p>
	 * For SQLDataSources foreign keys can be automatically discovered from SQL
	 * tables if
	 * {@link com.smartgwt.client.docs.serverds.DataSource#autoDeriveSchema
	 * autoDeriveSchema} is set.
	 * 
	 * @param foreignKey
	 *            . See {@link com.smartgwt.client.docs.String String}. Default
	 *            value is false
	 * @see com.smartgwt.client.docs.serverds.DataSourceField#joinType
	 * @see com.smartgwt.client.docs.DataSourceRelations DataSourceRelations
	 *      overview and related methods
	 */
	private String foreignKey;

	/**
	 * For use in {@link com.smartgwt.client.docs.ComponentSchema}, indicates
	 * what group to place the property in when editing in Visual Builder.
	 * 
	 * @param group
	 *            . See {@link com.smartgwt.client.docs.String String}. Default
	 *            value is null
	 * @see com.smartgwt.client.docs.ComponentSchema ComponentSchema overview
	 *      and related methods
	 */
	private String groupName;

	/**
	 * Whether this field should be hidden from users by default within a
	 * DataBound component. This is generally used for internal IDs and other
	 * fields not meaningful to users.
	 * <P>
	 * See {@link com.smartgwt.client.data.DataSourceField#getDetail detail} for
	 * fields that should be hidden in a summary view such as a
	 * {@link com.smartgwt.client.widgets.grid.ListGrid}, but still available to
	 * the user.
	 * <p>
	 * <b>NOTE:</b> This property is <b>not</b> a security setting - data for
	 * hidden fields is still delivered to the client, it just isn't shown to
	 * the user. If you wish to make sure that only appropriate data reaches the
	 * client, use
	 * {@link com.smartgwt.client.docs.serverds.OperationBinding#outputs
	 * outputs}, {@link com.smartgwt.client.data.DataSourceField#getCanView
	 * canView}:false on the field, or a field-level declarative security
	 * setting like
	 * {@link com.smartgwt.client.docs.serverds.DataSourceField#viewRequiresRole
	 * viewRequiresRole}.
	 * 
	 * @param hidden
	 *            hidden Default value is false
	 * @see com.smartgwt.client.docs.ComponentBinding ComponentBinding overview
	 *      and related methods
	 */
	private Boolean hidden = false;

	/**
	 * Whether this field should be completely excluded from this dataSource, as
	 * if it had never been defined.
	 * 
	 * 
	 * @return Boolean
	 * @see com.smartgwt.client.docs.ComponentBinding ComponentBinding overview
	 *      and related methods
	 */
	private Boolean ignored;

	/**
	 * For use in {@link com.smartgwt.client.docs.ComponentSchema}, a field
	 * {@link com.smartgwt.client.data.DataSource#getInheritsFrom inherited}
	 * from another schema can be redeclared with this property set in order to
	 * indicate that the property should not be used.
	 * <P>
	 * This is primarily used to influence
	 * {@link com.smartgwt.client.docs.VisualBuilder}. For simple type
	 * properties, this avoids the property appearing in the Component Editor.
	 * <P>
	 * For fields that hold subcomponents, this prevents inappropriate drag and
	 * drop. For example, a custom class called <code>MyDialog</code> may
	 * automatically create a series of children, and not allow arbitrary other
	 * children to be added. In this case, the inherited property
	 * {@link com.smartgwt.client.widgets.Canvas#getChildren children} should be
	 * marked inapplicable in order to prevent arbitrary components being
	 * dropped onto a <code>MyDialog</code> instance.
	 * 
	 * @param inapplicable
	 *            inapplicable Default value is null
	 * @see com.smartgwt.client.docs.ComponentSchema ComponentSchema overview
	 *      and related methods
	 */
	private Boolean inapplicable;

	/**
	 * Maximum number of characters allowed. Applicable only to fields of text
	 * type.
	 * <P>
	 * <b>NOTE:</b> For DataSources of type "sql", this property has a bearing
	 * on the type of column we use when the underlying table is created by a
	 * DataSource {@link com.smartgwt.client.docs.SqlDataSource import} in the
	 * {@link com.smartgwt.client.docs.AdminConsole Admin Console}. Below a
	 * certain length (which is database-specific, see below), we use standard
	 * <code>VARCHAR</code> columns; above that length, we use an alternate
	 * strategy (again, database-specific). For these long fields, we sometimes
	 * also generate different SQL for "update" and "add" operations, using JDBC
	 * "?" replacement parameters rather than embedding values directly in the
	 * generated SQL; whether or not this is done depends entirely on what the
	 * underlying database product and/or JDBC driver will allow.
	 * <P>
	 * <br>
	 * <b>Table of field length limits for supported databases:</b>
	 * <p>
	 * <table style="font-size:10px;text-align:center;border:1px solid black;">
	 * <tr>
	 * <td * style="color:white;background-color:black;"><b>Database product</b>
	 * </td>
	 * <td * style="color:white;background-color:black;"><b>VARCHAR limit *</b></td>
	 * <td * style="color:white;background-color:black;"><b>Type used above
	 * limit</b></td>
	 * </tr>
	 * <tr>
	 * <td>HSQLDB</td>
	 * <td>None</td>
	 * <td>-</td>
	 * </tr>
	 * <tr>
	 * <td>IBM DB2</td>
	 * <td>4000</td>
	 * <td>CLOB</td>
	 * </tr>
	 * <tr>
	 * <td>Firebird</td>
	 * <td>32767</td>
	 * <td>BLOB with subtype 1</td>
	 * </tr>
	 * <tr>
	 * <td>Informix</td>
	 * <td>255 / 32739</td>
	 * <td>LVARCHAR / TEXT **</td>
	 * </tr>
	 * <tr>
	 * <td>Microsoft SQL Server</td>
	 * <td>8000</td>
	 * <td>TEXT</td>
	 * </tr>
	 * <tr>
	 * <td>MySQL</td>
	 * <td>255 / 65535 / 16M</td>
	 * <td>TEXT / MEDIUMTEXT / LONGTEXT ***</td>
	 * </tr>
	 * <tr>
	 * <td>Oracle</td>
	 * <td>4000</td>
	 * <td>CLOB</td>
	 * </tr>
	 * <tr>
	 * <td>PostgreSQL</td>
	 * <td>4000</td>
	 * <td>TEXT</td>
	 * </tr>
	 * </table>
	 * <br>
	 * <b>*</b> The "VARCHAR limit" referred to here is a limit used by the
	 * Smart GWT Server; it is not necessarily imposed by the database. For
	 * example, DB2's VARCHAR limit is not 4000 characters; it actually varies
	 * from about 4K to about 32K, depending on how the server has been
	 * configured.
	 * <p>
	 * <b>**</b> Informix has a limit of just 255 characters for VARCHAR, but
	 * has a native LVARCHAR type which supports nearly 32K characters without
	 * needing to fall back on long datatypes. Therefore, with that one product,
	 * we have two thresholds for a change in storage type.
	 * <p>
	 * <b>***</b> MySQL has a limit of 255 characters for VARCHAR, 65,535
	 * characters for TEXT and 16,777,215 for MEDIUMTEXT; therefore, with that
	 * one product, we have three thresholds for a change in storage type.
	 * 
	 * @param length
	 *            length Default value is null
	 * @see <a
	 *      href="http://www.smartclient.com/smartgwt/showcase/#grid_datatypes_longtext"
	 *      target="examples">Long Text Example</a>
	 */
	private Integer length;

	/**
	 * Indicates that this field should always be Array-valued. If the value
	 * derived from {@link com.smartgwt.client.data.DataSource#getDataFormat XML
	 * or JSON data} is singular, it will be wrapped in an Array.
	 * <P>
	 * Specifically for XML serialization and deserialization,
	 * <code>multiple:true</code> behaves similarly to the <a
	 * href='http://www.google.com/search?hl=en&q=soap+array' onclick=
	 * "window.open('http://www.google.com/search?hl=en&q=soap+array');return false;"
	 * >SOAP array idiom</a>, that is, there will be a "wrapper element" named
	 * after the field name, whose contents will be several elements of the
	 * specified {@link com.smartgwt.client.data.DataSourceField#getType
	 * field.type}.
	 * <P>
	 * For example, {@link com.smartgwt.client.widgets.layout.Layout#getMembers
	 * members} is declared with <code>type:"Canvas",
	 *  multiple:true</code>. The correct XML format is thus:
	 * 
	 * <pre>
	 *  &lt;VLayout&gt;
	 *      &lt;members&gt;
	 *          &lt;Canvas ID="myCanvas" ... /&gt;
	 *          &lt;ListGrid ID="myGrid" .../&gt;
	 *          &lt;Toolstrip ID="myToolStrip" ... /&gt;
	 *      &lt;/members&gt;
	 *  &lt;/VLayout&gt;
	 * </pre>
	 * <P>
	 * See {@link com.smartgwt.client.data.DataSourceField#getChildTagName
	 * childTagName} for customizing the tagName used for subelements.
	 * 
	 * @param multiple
	 *            multiple Default value is null
	 * @see com.smartgwt.client.docs.ComponentSchema ComponentSchema overview
	 *      and related methods
	 */
	private Boolean multiple = false;

	/**
	 * Name for this field.
	 * <p>
	 * The field name is also the property in each DataSource record which holds
	 * the value for this field.
	 * <P>
	 * Must be unique across all fields within the DataSource and should be a
	 * valid JS identifier (with no dots, spaces, or other non-alphanumeric
	 * characters except underscore).
	 * 
	 * @param name
	 *            . See {@link com.smartgwt.client.docs.String String}. Default
	 *            value is null
	 * @see com.smartgwt.client.docs.Basics Basics overview and related methods
	 */
	// private String name;

	/**
	 * Controls whether an explicit null-valued Record attribute for this field
	 * should result in <code>xsi:nil</code> being used to transmit the value
	 * when serializing to XML, like so:
	 * 
	 * <pre>
	 *  &lt;book&gt;
	 *      &lt;title&gt;Beowulf&lt;/title&gt;
	 *      &lt;author xsi:nil="true"/&gt;
	 *  &lt;/book&gt;
	 * </pre>
	 * 
	 * If <code>nillable</code> is not set, no XML element will be generated for
	 * the explicit null value.
	 * 
	 * @param nillable
	 *            nillable Default value is null
	 */
	private Boolean nillable = true;

	/**
	 * Indicates <b>either</b> that this field holds a value unique across all
	 * records in this DataSource, <b>or</b> that it is one of a number of
	 * fields marked as primary keys, and the combination of the values held in
	 * all of those fields is unique across all records in the DataSource. Note
	 * that the latter usage - so-called "composite" or "multipart" keys - is
	 * intended for support of legacy databases only: if you are able to choose
	 * an approach, Isomorphic recommends the use of one <code>primaryKey</code>
	 * field per DataSource, and ideally this field should be of
	 * {@link com.smartgwt.client.data.DataSourceField#getType type} "sequence".
	 * <p>
	 * A DataSource that can <i>only</i> perform the "fetch" operation does not
	 * require a primaryKey. If a DataSource allows modification of DataSource
	 * records through add, update and remove DataSource operations, one or more
	 * fields must be marked as the primary key.
	 * <P>
	 * Smart GWT requires a primary key value to uniquely identify records when
	 * communicating updates or deletions to the server. There is no requirement
	 * that the primaryKey field be mapped to an actual "primary key" in your
	 * object model, web service, or database (though this is the most obvious
	 * and natural thing to do, of course). The only requirement is that the
	 * combined values of the primaryKey fields be unique for a given browser
	 * instance for the lifetime of the page.
	 * <P>
	 * If using Smart GWT's {@link com.smartgwt.client.docs.SqlDataSource SQL
	 * engine} and generating SQL tables using the
	 * {@link com.smartgwt.client.docs.AdminConsole Admin Console}, the table
	 * column generated from a primaryKey field will have a unique constraint
	 * applied in the database table and, if the field is of type "sequence",
	 * the database column will also be created as an "identity column" in those
	 * databases that implement sequence-type handling with identity columns.
	 * 
	 * @param primaryKey
	 *            primaryKey Default value is false
	 * @see com.smartgwt.client.docs.DataSourceRelations DataSourceRelations
	 *      overview and related methods
	 */
	private Boolean primaryKey = false;

	/**
	 * For use in {@link com.smartgwt.client.docs.ComponentSchema} for fields
	 * that contain other components, this flag suppresses auto-construction for
	 * subcomponents that appear under this field.
	 * <P>
	 * For example, the {@link com.smartgwt.client.widgets.layout.VLayout}
	 * schema sets this for its
	 * {@link com.smartgwt.client.widgets.layout.Layout#getMembers members}
	 * property, so that when a VLayout is constructed via XML as follows:
	 * 
	 * <pre>
	 *  &lt;VLayout&gt;
	 *      &lt;members&gt;
	 *          &lt;ListGrid ID="myGrid" .../&gt;
	 *          &lt;Toolstrip ID="myToolStrip" ... /&gt;
	 *      &lt;/members&gt;
	 *  &lt;/VLayout&gt;
	 * </pre>
	 * 
	 * The ListGrid and ToolStrip do not construct themselves automatically.
	 * Instead, the VLayout receives the properties of the ListGrid and
	 * ToolStrip as ordinary JavaScript Objects, with the special property
	 * <code>_constructor</code> set to the name of the class that should be
	 * constructed.
	 * 
	 * @param propertiesOnly
	 *            propertiesOnly Default value is null
	 * @see com.smartgwt.client.docs.ComponentSchema ComponentSchema overview
	 *      and related methods
	 */
	private Boolean propertiesOnly = false;

	/**
	 * Indicates this field must be non-null in order for a record to pass
	 * validation.
	 * <P>
	 * Note that <code>required</code> should not be set for a server-generated
	 * field, such as a sequence, or validation will fail on the client.
	 * 
	 * @param required
	 *            required Default value is null
	 */
	private Boolean required = false;

	/**
	 * For a field that is a foreignKey establishing a tree relationship, what
	 * value indicates a root-level node. Defaults to null.
	 * <P>
	 * Note that the rootValue may be overridden on a specific ResultTree
	 * instance by setting
	 * {@link com.smartgwt.client.widgets.tree.ResultTree#getRootNode rootNode},
	 * or if the ResultTree is auto-generated by a
	 * {@link com.smartgwt.client.widgets.tree.TreeGrid}, by setting
	 * {@link com.smartgwt.client.widgets.tree.TreeGrid#getTreeRootValue
	 * treeRootValue}. This allows a component to navigate a subtree of the
	 * hierarchical data from this dataSource starting at a particular node.
	 * 
	 * @param rootValue
	 *            rootValue Default value is null
	 * @see com.smartgwt.client.docs.DataSourceRelations DataSourceRelations
	 *      overview and related methods
	 * 
	 */

	/**
	 * For a DataSource with
	 * {@link com.smartgwt.client.docs.serverds.DataSource#serverType
	 * serverType:"sql"} with a field of type "sequence", the name of the SQL
	 * sequence that should be used when inserting new records into this table.
	 * <P>
	 * Note that this is never required for SQL tables that are generated from
	 * Smart GWT DataSources (a default sequence name of tableName + "_" +
	 * columnName is chosen), and is never required for databases where
	 * inserting null into a sequence column is sufficient (MySQL, SQL Server,
	 * DB2 and others).
	 * <P>
	 * You would only need to set sequenceName if you are integrating with a
	 * pre-existing table stored in a database where the sequence must be named
	 * for insertion to work (Oracle, Postgres, Firebird) OR you are trying to
	 * use the same sequence across multiple DataSources.
	 * 
	 * @param sequenceName
	 *            . See {@link com.smartgwt.client.docs.String String}. Default
	 *            value is null
	 * @see com.smartgwt.client.docs.SqlDataSource SqlDataSource overview and
	 *      related methods
	 */
	private String sequenceName;

	/**
	 * For a field of type:"imageFile", indicates whether to stream the image
	 * and display it inline or to display the View and Download icons.
	 * 
	 * @param showFileInline
	 *            showFileInline Default value is null
	 */
	private Boolean showFileInline = false;

	/**
	 * Title to show in a Summary of type "title" for this field. If unspecified
	 * <code>title</code> summaries will show the
	 * {@link com.smartgwt.client.data.DataSourceField#getTitle title} for the
	 * field.
	 * 
	 * @param summaryValueTitle
	 *            . See {@link com.smartgwt.client.docs.String String}. Default
	 *            value is null
	 */
	private String summaryValueTitle;

	/**
	 * Preferred time-format to apply to date type values within this field. If
	 * this property is specified on a field displayed within a dataBound
	 * component such as a {@link com.smartgwt.client.widgets.grid.ListGrid} or
	 * {@link com.smartgwt.client.widgets.form.DynamicForm}, any dates displayed
	 * in this field will be formatted as times using the appropriate format.
	 * <P>
	 * This is most commonly only applied to fields specified as type
	 * <code>"time"</code> though if no explicit
	 * {@link com.smartgwt.client.widgets.form.fields.FormItem#getDateFormatter
	 * dateFormatter} is specified it will be respected for other fields as
	 * well.
	 * <P>
	 * See
	 * {@link com.smartgwt.client.widgets.grid.ListGridField#getTimeFormatter
	 * timeFormatter} and
	 * {@link com.smartgwt.client.widgets.form.fields.FormItem#getTimeFormatter
	 * timeFormatter} for more information.
	 * <p>
	 * <b>Note : </b> This is an advanced setting
	 * </p>
	 * 
	 * @param timeFormatter
	 *            timeFormatter Default value is null
	 * @see com.smartgwt.client.docs.Appearance Appearance overview and related
	 *      methods
	 */

	/**
	 * Default user-visible title for this field.
	 * <p>
	 * This will be picked up by DataBound components and other views over this
	 * DataSource.
	 * <p>
	 * Note this property frequently does not need to be set since
	 * {@link com.smartgwt.client.data.DataSource#getAutoDeriveTitles
	 * autoDeriveTitles} (on by default) usually picks an appropriate
	 * user-visible title if you have a reasonable naming convention for your
	 * field names.
	 * <P>
	 * Note that if this field is being displayed in a
	 * {@link com.smartgwt.client.widgets.grid.ListGrid} bound to this
	 * dataSource, the
	 * {@link com.smartgwt.client.widgets.grid.ListGridField#getHeaderTitle
	 * headerTitle} attribute may be used to specify a different string for
	 * display in the listGrid column header.
	 * 
	 * @param title
	 *            . See {@link com.smartgwt.client.docs.String String}. Default
	 *            value is null
	 * @see com.smartgwt.client.docs.ComponentBinding ComponentBinding overview
	 *      and related methods
	 */
	private String title;

	/**
	 * Type of this field. Required for all DataSource fields.
	 * <P>
	 * Field type may imply automatic validators (for example, an integer field
	 * cannot accept the value "foo"). Field type also affects the default
	 * behaviors of DataBound components, for example, if a field is declared as
	 * type "date", components that edit that field will automatically choose a
	 * date-editing interface with pop-up date picker.
	 * 
	 * @param type
	 *            type Default value is null
	 * @see com.smartgwt.client.docs.Basics Basics overview and related methods
	 */

	/**
	 * Type of this field. Required for all DataSource fields.
	 * <P>
	 * Field type may imply automatic validators (for example, an integer field
	 * cannot accept the value "foo"). Field type also affects the default
	 * behaviors of DataBound components, for example, if a field is declared as
	 * type "date", components that edit that field will automatically choose a
	 * date-editing interface with pop-up date picker.
	 * 
	 * 
	 * @return FieldType
	 * @see com.smartgwt.client.docs.Basics Basics overview and related methods
	 */
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private AbstractType dataType;

	/**
	 * Validators to be applied to this field.
	 * <p>
	 * Validators are applied whenever there is an attempt to save changes to a
	 * field.
	 * <p>
	 * For the available set of built-in validators, and how to define a custom
	 * validator, see the
	 * {@link com.smartgwt.client.widgets.form.validator.Validator} class.
	 * 
	 * @param validators
	 *            validators Default value is null
	 * @see com.smartgwt.client.widgets.form.validator.Validator
	 * @see com.smartgwt.client.docs.Validation Validation overview and related
	 *      methods
	 */
//	@OneToMany
//	Set<Validator> validators = new HashSet<Validator>();

	/**
	 * A ValueMap is a set of legal values for a field.
	 * <p>
	 * The valueMap can be specified as either an Array of legal values, or as
	 * an Object where each property maps a stored value to a user-displayable
	 * value.
	 * <p>
	 * To enforce that a field should be constrained to only the values in the
	 * valueMap, either declare
	 * {@link com.smartgwt.client.data.DataSourceField#getType field.type} as
	 * "enum", or use a {@link com.smartgwt.client.types.ValidatorType} of
	 * "isOneOf" with explicitly listed values. Otherwise, although a normal
	 * {@link com.smartgwt.client.widgets.form.fields.SelectItem} control will
	 * only allow values from the valueMap to be entered, other controls such as
	 * a {@link com.smartgwt.client.widgets.form.fields.ComboBoxItem ComboBox}
	 * will allow other values to be entered.
	 * <p>
	 * In XML, a valueMap that specifies only a list of legal values is
	 * specified as follows:
	 * 
	 * <pre>
	 *    &lt;valueMap&gt;
	 *     &lt;value&gt;Pens &amp; Pencils&lt;/value&gt;
	 *     &lt;value&gt;Stationery&lt;/value&gt;
	 *     &lt;value&gt;Computer Products&lt;/value&gt;
	 *     &lt;value&gt;Furniture&lt;/value&gt;
	 *     &lt;value&gt;Misc&lt;/value&gt;
	 *    &lt;/valueMap&gt;
	 * </pre>
	 * 
	 * A ValueMap that specifies stored values mapped to user-visible values is
	 * specified as follows:
	 * 
	 * <pre>
	 *    &lt;valueMap&gt;
	 *     &lt;value ID="1"&gt;Pens &amp; Pencils&lt;/value&gt;
	 *     &lt;value ID="2"&gt;Stationery&lt;/value&gt;
	 *     &lt;value ID="3"&gt;Computer Products&lt;/value&gt;
	 *     &lt;value ID="4"&gt;Furniture&lt;/value&gt;
	 *     &lt;value ID="5"&gt;Misc&lt;/value&gt;
	 *    &lt;/valueMap&gt;
	 * </pre>
	 * 
	 * @param valueMap
	 *            valueMap Default value is null
	 */

	/**
	 * XPath expression used to retrieve the field's value.
	 * <P>
	 * This XPath expression will be evaluated in the scope of the record
	 * objects selected by the
	 * {@link com.smartgwt.client.data.DataSource#getRecordXPath recordXPath}.
	 * For XML data ({@link com.smartgwt.client.data.DataSource#getDataFormat
	 * dataFormat:"xml"}) this means a call to
	 * {@link com.smartgwt.client.data.XMLTools#selectString
	 * XMLTools.selectString} passing the selected XML element. For JSON data (
	 * {@link com.smartgwt.client.data.DataSource#getDataFormat
	 * dataFormat:"json"}), this means a call to
	 * {@link com.smartgwt.client.data.XMLTools#selectObjects
	 * XMLTools.selectObjects} passing the selected JSON object.
	 * <P>
	 * In the absence of a <code>valueXPath</code>, for JSON data the value for
	 * the field will be the value of the same-named property in the record
	 * object selected by
	 * {@link com.smartgwt.client.data.DataSource#getRecordXPath recordXPath}.
	 * <P>
	 * For XML data, the value will be the attribute or subelement named after
	 * the field name. For example, for a field "author" on a record element
	 * &lt;book&gt;, the following structures require no valueXPath:
	 * 
	 * <pre>
	 *     &lt;book author="Mark Jones"/&gt;
	 * 
	 *     &lt;book&gt;
	 *         &lt;author&gt;Mark Jones&lt;/author&gt;
	 *     &lt;/book&gt;
	 * </pre>
	 * <P>
	 * If <code>valueXPath</code> is not required for your field because of the
	 * default handling described above, don't specify it, as it's slightly
	 * slower.
	 * <p>
	 * To learn about XPath, try the following search: <a
	 * href="http://www.google.com/search?q=xpath+tutorial" target="_blank"
	 * >http://www.google.com/search?q=xpath+tutorial</a>
	 * <P>
	 * <B>Using valueXPath with the Smart GWT server</B>
	 * <p>
	 * If you're using the Smart GWT server to return data via the DSResponse
	 * object (or indirectly doing so using DataSource DMI), the valueXPath you
	 * specify on the DataSource fields will be applied to the data you return
	 * via the <a href='http://jakarta.apache.org/commons/jxpath/' onclick=
	 * "window.open('http://jakarta.apache.org/commons/jxpath/');return false;"
	 * >JXPath</a> library.
	 * <P>
	 * If you are returning Java Beans as your DSResponse data, normally each
	 * dataSource field receives the value of the same-named Java Bean property,
	 * that is, a field "zipCode" is populated by looking for "getZipCode()" on
	 * the objects passed as DSResponse data. You can use
	 * <code>valueXPath</code> to retrieve properties from subobjects, so long
	 * as a chain of getter methods exists that corresponds to the valueXPath.
	 * For example, a valueXPath of "address/zipCode" expects to call
	 * "getAddress()" on the bean(s) passed to DSResponse.setData(), followed by
	 * "getZipCode()" on whatever object "getAddress()" returns.
	 * <P>
	 * When you are saving data, the inbound DSRequest values, available as a
	 * Java Map, will use just dataSource field names as Map keys, not the
	 * valueXPath used to derive them. However, to achieve bidirectional
	 * valueXPath binding, you can use the server-side method
	 * dataSource.setProperties() to use the valueXPath when setting properties
	 * on your server object model. When applied as a setter, an XPath like
	 * "address/zipCode" attempts "getAddress()" followed by "setZipCode()" on
	 * the returned object. JXPath also has some ability to auto-create
	 * intervening objects if they are missing, such as auto-creating an
	 * "address" subobject when applying "address/zipCode" as a valueXPath.
	 * <P>
	 * See the <a href='http://jakarta.apache.org/commons/jxpath/' onclick=
	 * "window.open('http://jakarta.apache.org/commons/jxpath/');return false;"
	 * >JXPath</a> library documentation for complete details, including other
	 * types of server object models supported, such as server-side XML.
	 * 
	 * @param valueXPath
	 *            . See {@link com.smartgwt.client.docs.XPathExpression
	 *            XPathExpression}. Default value is null
	 * @see com.smartgwt.client.docs.ClientDataIntegration ClientDataIntegration
	 *      overview and related methods
	 * @see <a
	 *      href="http://www.smartclient.com/smartgwt/showcase/#xpath_xml_integration_category"
	 *      target="examples">XPath Binding Example</a>
	 */
	private String valueXPath;

	/**
	 * Indicates that {@link com.smartgwt.client.data.DataSource#xmlSerialize
	 * DataSource.xmlSerialize} should serialize this value as an XML attribute.
	 * <P>
	 * Note this does not need to be declared in order for DataSource records to
	 * be derived from XML data: a field will be populated with either an
	 * attribute or subelement with matching name.
	 * 
	 * @param xmlAttribute
	 *            xmlAttribute Default value is null
	 * @see com.smartgwt.client.docs.ComponentSchema ComponentSchema overview
	 *      and related methods
	 */
	private Boolean xmlAttribute = true;

	/**
	 * Set the plural title.
	 * 
	 * @param pluralTitle
	 *            the plural title
	 */
	private String pluralTitle;

	/**
	 * Sets the default FormItem to be used whenever this field is edited
	 * (whether in a grid, form, or other component).
	 * <P>
	 * If unset, a FormItem will be automatically chosen based on the type of
	 * the field.
	 * <p>
	 * <br>
	 * Note: the FormItem passed to setEditorType() is used as a "template" to
	 * create a FormItem whenever a DataBoundComponent needs to show an
	 * interface for editing this field. This means you need to follow special
	 * rules:
	 * <ol>
	 * <li>In event handler code, you must obtain the current FormItem instance
	 * from the provided Event object via getItem(). You cannot make method
	 * calls via "this" or via implicit instance scope: both "clearValue()" and
	 * "this.clearValue()" need to be written as "item.clearValue()" instead
	 * (where "item" is the result of event.getItem()).</li>
	 * <li>To store custom instance variables, you must use
	 * FormItem.getAttribute()/setAttribute() (or their type-specific variants).
	 * You cannot store and retrieve instance variables via "this" -
	 * "this.someVariable = 5" will not work.</li>
	 * <li>You may not override superclass methods - your behaviors have to be
	 * implemented via event handlers</li>
	 * <li>If you create a custom subclass, the FormItem you receive in an event
	 * handler will be of a generic type and must be converted before you can
	 * call custom methods. Conversion is done via
	 * <code>new MyCustomItem(item.getJsObj());</code> (complete code sample
	 * below).<br>
	 * Note that this conversion does not actually cause creation or rendering
	 * of a new widget and is comparable in cost to a typecast.</li>
	 * </ol>
	 * Example code demonstrating using an eventHandler to call a method on
	 * custom subclass of TextItem:
	 * 
	 * <pre>
	 * class MyCustomItem extends TextItem {
	 *      MyCustomItem (JavaScriptObject config) {
	 *      }
	 *      
	 *      MyCustomItem(String name) {
	 *          setInitHandler(new FormItemInitHandler() {
	 *              public void onInit(FormItem item) {
	 *                  // correct
	 *                  new MyCustomItem(item.getJsObj()).customMethod();
	 *                  
	 *                  // incorrect, will throw an error
	 *                  // ((MyCustomItem)item).customMethod();
	 *              }
	 *          }
	 *      }
	 *      
	 *      void customMethod() { ... }
	 *  }
	 *  
	 *  ...
	 *  
	 *  myDataSource.setEditorType(new MyCustomItem("field1"));
	 * </pre>
	 * 
	 * @param editorType
	 *            editorType Default value is null
	 */
	// private FormItemRepresentation editorType;

	/**
	 * 
	 * Sets the default FormItem to be used if this field is marked as
	 * {@link #setCanEdit,canEdit false} and displayed in an editor component
	 * such as a DynamicForm.
	 * <P>
	 * This property may also be specified at the type level by specifying
	 * {@link SimpleType.setReadOnlyEditorType()}.
	 * 
	 * @param editorType
	 *            editorType Default value is null
	 */
	// private FormItemRepresentation readOnlyEditorType;

	/**
	 * For a field that is a foreignKey establishing a tree relationship, what
	 * value indicates a root-level node. Defaults to null.
	 * 
	 * @param rootValue
	 *            rootValue Default value is null
	 */
	private String rootValue;

	/**
	 * Function to retrieve the field's value from the XML element or JSON
	 * record returned from a web service.
	 * <P>
	 * This is an advanced API for use when a
	 * {@link com.smartgwt.client.data.DataSourceField#getValueXPath valueXPath}
	 * setting is insufficient to derive a field's value, yet an implementation
	 * of {@link com.smartgwt.client.data.DataSource#transformResponse} is
	 * overkill.
	 * 
	 * @param extractor
	 *            the field value extractor
	 */
	// private FieldValueExtractor extractor;

	/**
	 * The type can also be the another DataSource, which allows you to model
	 * nested structures such as XML documents (in fact,
	 * XMLTools.loadXMLSchema() models XML schema in this way). Nested
	 * DataSource declarations affect how XML and JSON data is deserialized into
	 * JavaScript objects in the client-side integration pipeline, so that you
	 * can load complex XML documents and have them deserialized into a
	 * correctly typed JavaScript object model.
	 * 
	 * @param dataSource
	 *            the data source
	 */
	@ManyToOne
	private DataSource dataSource;

	/**
	 * The parent data source
	 * 
	 * @param dataSource
	 *            the parent data source
	 */
	@ManyToOne
	private DataSource parentDataSource;

	/**
	 * If {@link com.smartgwt.client.widgets.grid.ListGrid#getShowGridSummary
	 * showGridSummary} or
	 * {@link com.smartgwt.client.widgets.grid.ListGrid#getShowGroupSummary
	 * showGroupSummary} is true, this attribute can be used to specify an
	 * explicit {@link com.smartgwt.client.widgets.grid.SummaryFunction} for
	 * calculating the summary value to display.
	 * 
	 * @param summaryFunction
	 *            summaryFunction Default value is null
	 */
	// private SummaryFunctionType summaryFunction;

	/**
	 * If {@link com.smartgwt.client.widgets.grid.ListGrid#getShowGridSummary
	 * showGridSummary} or
	 * {@link com.smartgwt.client.widgets.grid.ListGrid#getShowGroupSummary
	 * showGroupSummary} is true, this attribute can be used to specify an
	 * explicit {@link com.smartgwt.client.widgets.grid.SummaryFunction} for
	 * calculating the summary value to display.
	 * 
	 * @param summaryFunction
	 *            summaryFunction Default value is null
	 */
	// private SummaryFunction summaryFunction;

	@SuppressWarnings("unused")
	private boolean isDataSourceField() {
		// TODO Make sure this gets looked at
		return dataSource != null;
	}

	/**
	 * Causes a tooltip hover to appear on the header generated for this data
	 * source field (effectively sets
	 * {@link com.smartgwt.client.widgets.Canvas#getPrompt prompt} for the
	 * header).
	 * 
	 * @param prompt
	 *            prompt Default value is null
	 */
	private String prompt;

	public Boolean getCanEdit() {
		return canEdit;
	}

	public void setCanEdit(Boolean canEdit) {
		this.canEdit = canEdit;
	}

	public Boolean getCanExport() {
		return canExport;
	}

	public void setCanExport(Boolean canExport) {
		this.canExport = canExport;
	}

	public Boolean getCanFilter() {
		return canFilter;
	}

	public void setCanFilter(Boolean canFilter) {
		this.canFilter = canFilter;
	}

	public Boolean getCanSave() {
		return canSave;
	}

	public void setCanSave(Boolean canSave) {
		this.canSave = canSave;
	}

	public Boolean getCanSort() {
		return canSort;
	}

	public void setCanSort(Boolean canSort) {
		this.canSort = canSort;
	}

	public Boolean getCanView() {
		return canView;
	}

	public void setCanView(Boolean canView) {
		this.canView = canView;
	}

	public Integer getDecimalPad() {
		return decimalPad;
	}

	public void setDecimalPad(Integer decimalPad) {
		this.decimalPad = decimalPad;
	}

	public Integer getDecimalPrecision() {
		return decimalPrecision;
	}

	public void setDecimalPrecision(Integer decimalPrecision) {
		this.decimalPrecision = decimalPrecision;
	}

	public Boolean getDetail() {
		return detail;
	}

	public void setDetail(Boolean detail) {
		this.detail = detail;
	}

	public Boolean getEscapeHTML() {
		return escapeHTML;
	}

	public void setEscapeHTML(Boolean escapeHTML) {
		this.escapeHTML = escapeHTML;
	}

	public String getExportTitle() {
		return exportTitle;
	}

	public void setExportTitle(String exportTitle) {
		this.exportTitle = exportTitle;
	}

	public String getForeignKey() {
		return foreignKey;
	}

	public void setForeignKey(String foreignKey) {
		this.foreignKey = foreignKey;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String group) {
		this.groupName = group;
	}

	public Boolean getHidden() {
		return hidden;
	}

	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}

	public Boolean getIgnore() {
		return ignored;
	}

	public void setIgnore(Boolean ignore) {
		this.ignored = ignore;
	}

	public Boolean getInapplicable() {
		return inapplicable;
	}

	public void setInapplicable(Boolean inapplicable) {
		this.inapplicable = inapplicable;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public Boolean getMultiple() {
		return multiple;
	}

	public void setMultiple(Boolean multiple) {
		this.multiple = multiple;
	}

	public Boolean getNillable() {
		return nillable;
	}

	public void setNillable(Boolean nillable) {
		this.nillable = nillable;
	}

	public Boolean getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(Boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	public Boolean getPropertiesOnly() {
		return propertiesOnly;
	}

	public void setPropertiesOnly(Boolean propertiesOnly) {
		this.propertiesOnly = propertiesOnly;
	}

	public Boolean getRequired() {
		return required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}

	public String getSequenceName() {
		return sequenceName;
	}

	public void setSequenceName(String sequenceName) {
		this.sequenceName = sequenceName;
	}

	public Boolean getShowFileInline() {
		return showFileInline;
	}

	public void setShowFileInline(Boolean showFileInline) {
		this.showFileInline = showFileInline;
	}

	public String getSummaryValueTitle() {
		return summaryValueTitle;
	}

	public void setSummaryValueTitle(String summaryValueTitle) {
		this.summaryValueTitle = summaryValueTitle;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public AbstractType getDataType() {
		return dataType;
	}

	public void setDataType(AbstractType dataType) {
		this.dataType = dataType;
	}

//	public Set<Validator> getValidators() {
//		return validators;
//	}
//
//	public void setValidators(Set<Validator> validators) {
//		this.validators = validators;
//	}

	public String getJPAPath() {
		if (org.flowframe.kernel.common.utils.Validator.isNotNull(valueXPath)) {
			String path = getName() + "." + valueXPath.replace('/', '.');
			return path;
		} else
			return null;
	}

	public Boolean isNestedAttribute() {
		if (org.flowframe.kernel.common.utils.Validator.isNotNull(getJPAPath()) && StringUtil.contains(getJPAPath(), ".", ""))
			return true;
		else
			return false;
	}

	public String getValueXPath() {
		return valueXPath;
	}

	public void setValueXPath(String valueXPath) {
		this.valueXPath = valueXPath;
	}

	public Boolean getXmlAttribute() {
		return xmlAttribute;
	}

	public void setXmlAttribute(Boolean xmlAttribute) {
		this.xmlAttribute = xmlAttribute;
	}

	public String getPluralTitle() {
		return pluralTitle;
	}

	public void setPluralTitle(String pluralTitle) {
		this.pluralTitle = pluralTitle;
	}

	public String getRootValue() {
		return rootValue;
	}

	public void setRootValue(String rootValue) {
		this.rootValue = rootValue;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public DataSource getParentDataSource() {
		return parentDataSource;
	}

	public void setParentDataSource(DataSource parentDataSource) {
		this.parentDataSource = parentDataSource;
	}

	public String getPrompt() {
		return prompt;
	}

	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}

	public String toString() {
		int indent = 0;

		StringBuilder sb = new StringBuilder();
		sb.append(DataSource.indent(indent) + "{\n");

		sb.append(DataSource.indent(indent + 1) + "name: " + getName());

		if (getDataType().getPersistentType() == PersistenceType.BASIC)
			sb.append(DataSource.indent(indent + 1) + "type: " + getDataType().getEntityJavaType());
		else
			sb.append(DataSource.indent(indent + 1) + "type: " + getDataSource().getEntityType().getJpaEntityName());

		sb.append(DataSource.indent(indent) + "}\n");

		return sb.toString();
	}

	public DataSourceField getParentDataSourceField() {
		return parentDataSourceField;
	}

	public void setParentDataSourceField(DataSourceField parentDataSourceField) {
		this.parentDataSourceField = parentDataSourceField;
	}

	public Set<DataSourceField> getChildDataSourceFields() {
		return childDataSourceFields;
	}

	public void setChildDataSourceFields(Set<DataSourceField> childDataSourceFields) {
		this.childDataSourceFields = childDataSourceFields;
	}

	public Set<DataSourceFieldValidator> getValidators() {
		return validators;
	}

	public Set<DataSourceFieldDependenceExpression> getDependenceExpressions() {
		return dependenceExpressions;
	}

	public void setDependenceExpressions(Set<DataSourceFieldDependenceExpression> dependenceExpressions) {
		this.dependenceExpressions = dependenceExpressions;
	}

	public int getOrdinal() {
		return ordinal;
	}

	public void setOrdinal(int ordinal) {
		this.ordinal = ordinal;
	}
	
}
