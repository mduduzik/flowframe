if (!ORYX.Plugins) {
    ORYX.Plugins = new Object();
}

if (!ORYX.Plugins.ETL) {
    ORYX.Plugins.ETL = new Object();
}

if (!ORYX.Plugins.ETL.Job) {
    ORYX.Plugins.ETL.Job = new Object();
}

if (!ORYX.Plugins.ETL.Job.Entry) {
    ORYX.Plugins.ETL.Job.Entry = new Object();
}

ORYX.Plugins.ETL.Job.Entry.JobEntryPropertyWindow = {
    canvas: undefined,
    facade: undefined,
    ns: ORYX.CONFIG.NAMESPACE_ETL_JOB,

    construct: function (facade) {
        // Reference to the Editor-Interface
        this.facade = facade;

        if (this.disRegardEvent())
            return;

        this.facade.registerOnEvent(ORYX.CONFIG.EVENT_SHOW_PROPERTYWINDOW, this.initPropGrid.bind(this));
        //this.facade.registerOnEvent(ORYX.CONFIG.EVENT_LOADED, this.onSelectDiagram.bind(this));
        //this.facade.registerOnEvent(ORYX.CONFIG.EVENT_SELECTION_CHANGED, this.onSelectionChanged.bind(this));
        //this.facade.registerOnEvent(ORYX.CONFIG.EVENT_HIGHLIGHT_HIDE, this.onHideHighlight.bind(this));

        this.initPropGrid();
    },
    disRegardEvent: function() {
        return !this.isCompatibleStencilSet(this.facade.getNamespace());
    },
    isCompatibleStencilSet: function(ssnamespace) {
      return (this.ns === ssnamespace);
    },
    initPropGrid: function (event,args) {
        // The parent div-node of the grid
        this.node = ORYX.Editor.graft("http://www.w3.org/1999/xhtml",
            null,
            ['div']);

        // If the current property in focus is of type 'Date', the date format
        // is stored here.
        this.currentDateFormat;

        // the properties array
        this.properties = [];

        /* The currently selected shapes whos properties will shown */
        this.shapeSelection = new Hash();
        this.shapeSelection.shapes = new Array();
        this.shapeSelection.commonProperties = new Array();
        this.shapeSelection.commonPropertiesValues = new Hash();

        this.updaterFlag = false;

        this.updaterFlag = false;


        //creating rowaction of the grid
        // Create RowActions Plugin
        this.action = new Ext.ux.grid.RowActions({
            header:''
//            ,autoWidth:true
//            ,hideMode:'display'
            ,keepSelection:true
            ,actions:[{
                iconIndex: 'selectCallback'
                ,iconCls:'dots_button'
                ,qtipIndex:'selectQtip'
                ,hideIndex:'hideSelect'
            },{
                iconIndex: 'editCallback'
                ,iconCls:'application_form_edit'
                ,qtipIndex:'editQtip'
                ,hideIndex:'hideEdit'
//				,text:'Open'
            }]
            ,callbacks:{
                'icon-plus':function(grid, record, action, row, col) {
                    Ext.ux.Toast.msg('Callback: icon-plus', 'You have clicked row: <b>{0}</b>, action: <b>{0}</b>', row, action);
                }.bind(this)
            }
        });

        // dummy action event handler - just outputs some arguments to console
        this.action.on({
            action:function(grid, record, action, row, col) {
                Ext.ux.Toast.msg('Event: action', 'You have clicked row: <b>{0}</b>, action: <b>{1}</b>', row, action);
            }
            ,beforeaction:function() {
                Ext.ux.Toast.msg('Event: beforeaction', 'You can cancel the action by returning false from this event handler.');
            }
            ,beforegroupaction:function() {
                Ext.ux.Toast.msg('Event: beforegroupaction', 'You can cancel the action by returning false from this event handler.');
            }
            ,groupaction:function(grid, records, action, groupId) {
                Ext.ux.Toast.msg('Event: groupaction', 'Group: <b>{0}</b>, action: <b>{1}</b>, records: <b>{2}</b>', groupId, action, records.length);
            }
        });

        // creating the column model of the grid.
        this.columnModel = new Ext.grid.ColumnModel([
            {
                //id: 'name',
                header: ORYX.I18N.PropertyWindow.name,
                dataIndex: 'name',
                width: 90,
                sortable: true,
                renderer: this.tooltipRenderer.bind(this)
            }, {
                //id: 'value',
                header: ORYX.I18N.PropertyWindow.value,
                dataIndex: 'value',
                id: 'propertywindow_column_value',
                width: 110,
                editor: new Ext.form.TextField({
                    allowBlank: false
                }),
                renderer: this.renderer.bind(this)
            },
            this.action,
            {
                header: "Category",
                dataIndex: 'category',
                hidden: true,
                sortable: true
            }
        ]);

        // creating the store for the model.
        this.dataSource = new Ext.data.GroupingStore({
            proxy: new Ext.data.MemoryProxy(this.properties),
            reader: new Ext.data.ArrayReader({}, [
                {name: 'category'},
                {name: 'name'},
                {name: 'value'},
                {name: 'icons'},
                {name: 'gridProperties'},
                {name: 'selectCallback', type: 'string'},
                {name: 'selectQtip', type: 'string'},
                {name: 'hideSelect', type: 'boolean'},
                {name: 'editCallback', type: 'string'},
                {name: 'editQtip', type: 'string'},
                {name: 'hideEdit', type: 'boolean'}
            ]),
            sortInfo: {field: 'category', direction: "ASC"},
            sortData : function(f, direction){
                direction = direction || 'ASC';
                var st = this.fields.get(f).sortType;
                var fn = function(r1, r2){
                    var v1 = st(r1.data[f]), v2 = st(r2.data[f]);
                    var p1 = r1.data['category'], p2  = r2.data['category'];
                    return p1 && !p2 ? -1 : (!p1 && p2 ? 1 : (v1 > v2 ? 1 : (v1 < v2 ? -1 : 0)));
                };
                this.data.sort(direction, fn);
                if(this.snapshot && this.snapshot != this.data){
                    this.snapshot.sort(direction, fn);
                }
            },
            groupField: 'category'
        });
        this.dataSource.load();

        this.grid = new Ext.grid.EditorGridPanel({
            clicksToEdit: 1,
            stripeRows: true,
            autoExpandColumn: "propertywindow_column_value",
            width:'auto',
            iconCls: 'palette-icon',
            title: 'Entry',
            // the column model
            colModel: this.columnModel,
            enableHdMenu: false,
            view: new Ext.grid.GroupingView({
                forceFit: true,
                groupTextTpl: '<p style="text-align:left;">{[values.rs.first().data.category ? values.rs.first().data.category : ORYX.I18N.PropertyWindow.moreProps]}</p>',
                bodyStyle: 'text-align: left;'

            }),
            plugins:[this.action],
            // the data store
            store: this.dataSource

        });

        /*new Ext.Panel({
         autoHeight: true,
         layout: "fit",
         border: false,
         iconCls: 'property-icon',
         title: 'Properties',
         items: [
         this.grid
         ]
         }*/
        region = this.facade.addToRegion("centerSouth", this.grid, "Component");

        // Register on Events
        this.grid.on('beforeedit', this.beforeEdit, this, true);
        this.grid.on('afteredit', this.afterEdit, this, true);
        this.grid.view.on('refresh', this.hideMoreAttrs, this, true);

        //this.grid.on(ORYX.CONFIG.EVENT_KEYDOWN, this.keyDown, this, true);

        // Renderer the Grid
        this.grid.enableColumnMove = false;
        //this.grid.render();

        // Sort as Default the first column
        //this.dataSource.sort('name');

        //region = this.facade.addToRegion("centerSouth", panel, "Component");

        // Register on Events
        /*		this.grid.on('beforeedit', this.beforeEdit, this, true);
         this.grid.on('afteredit', this.afterEdit, this, true);
         this.grid.view.on('refresh', this.hideMoreAttrs, this, true);*/

        //this.grid.on(ORYX.CONFIG.EVENT_KEYDOWN, this.keyDown, this, true);

        // Renderer the Grid
        //this.grid.enableColumnMove = false;
        //this.grid.render();

        // Sort as Default the first column
        //this.dataSource.sort('name');

    },

    // Select the Canvas when the editor is ready
    selectDiagram: function () {
        this.shapeSelection.shapes = [this.facade.getCanvas()];

        this.setPropertyWindowTitle();
        this.identifyCommonProperties();
        this.createProperties();
    },

    specialKeyDown: function (field, event) {
        // If there is a TextArea and the Key is an Enter
        if (field instanceof Ext.form.TextArea && event.button == ORYX.CONFIG.KEY_Code_enter) {
            // Abort the Event
            return false
        }
    },
    tooltipRenderer: function (value, p, record) {
        /* Prepare tooltip */
        p.cellAttr = 'title="' + record.data.gridProperties.tooltip + '"';
        return value;
    },

    renderer: function (value, p, record) {

        this.tooltipRenderer(value, p, record);

        if (value instanceof Date) {
            // TODO: Date-Schema is not generic
            value = value.dateFormat(ORYX.I18N.PropertyWindow.dateFormat);
        } else if (String(value).search("<a href='") < 0) {
            // Shows the Value in the Grid in each Line
            value = String(value).gsub("<", "&lt;");
            value = String(value).gsub(">", "&gt;");
            value = String(value).gsub("%", "&#37;");
            value = String(value).gsub("&", "&amp;");

            if (record.data.gridProperties.type == ORYX.CONFIG.TYPE_COLOR) {
                value = "<div class='prop-background-color' style='background-color:" + value + "' />";
            }

            record.data.icons.each(function (each) {
                if (each.name == value) {
                    if (each.icon) {
                        value = "<img src='" + each.icon + "' /> " + value;
                    }
                }
            });
        }

        return value;
    },

    beforeEdit: function (option) {

        var editorGrid = this.dataSource.getAt(option.row).data.gridProperties.editor;
        var editorRenderer = this.dataSource.getAt(option.row).data.gridProperties.renderer;

        if (editorGrid) {
            // Disable KeyDown
            this.facade.disableEvent(ORYX.CONFIG.EVENT_KEYDOWN);

            option.grid.getColumnModel().setEditor(1, editorGrid);

            editorGrid.field.row = option.row;
            // Render the editor to the grid, therefore the editor is also available
            // for the first and last row
            editorGrid.render(this.grid);

            //option.grid.getColumnModel().setRenderer(1, editorRenderer);
            editorGrid.setSize(option.grid.getColumnModel().getColumnWidth(1), editorGrid.height);
        } else {
            return false;
        }

        var key = this.dataSource.getAt(option.row).data.gridProperties.propId;

        this.oldValues = new Hash();
        this.shapeSelection.shapes.each(function (shape) {
            this.oldValues[shape.getId()] = shape.properties[key];
        }.bind(this));
    },

    afterEdit: function (option) {
        //Ext1.0: option.grid.getDataSource().commitChanges();
        option.grid.getStore().commitChanges();

        var key = option.record.data.gridProperties.propId;
        var selectedElements = this.shapeSelection.shapes;

        var oldValues = this.oldValues;

        var newValue = option.value;
        var facade = this.facade;


        // Implement the specific command for property change
        var commandClass = ORYX.Core.Command.extend({
            construct: function () {
                this.key = key;
                this.selectedElements = selectedElements;
                this.oldValues = oldValues;
                this.newValue = newValue;
                this.facade = facade;
            },
            execute: function () {
                this.selectedElements.each(function (shape) {
                    if (!shape.getStencil().property(this.key).readonly()) {
                        shape.setProperty(this.key, this.newValue);
                    }
                }.bind(this));
                this.facade.setSelection(this.selectedElements);
                this.facade.getCanvas().update();
                this.facade.updateSelection();
            },
            rollback: function () {
                this.selectedElements.each(function (shape) {
                    shape.setProperty(this.key, this.oldValues[shape.getId()]);
                }.bind(this));
                this.facade.setSelection(this.selectedElements);
                this.facade.getCanvas().update();
                this.facade.updateSelection();
            }
        })
        // Instanciated the class
        var command = new commandClass();

        // Execute the command
        this.facade.executeCommands([command]);


        // extended by Kerstin (start)
//
        this.facade.raiseEvent({
            type: ORYX.CONFIG.EVENT_PROPWINDOW_PROP_CHANGED,
            elements: selectedElements,
            key: key,
            value: option.value
        });
        // extended by Kerstin (end)
    },

    // Cahnges made in the property window will be shown directly
    editDirectly: function (key, value) {

        this.shapeSelection.shapes.each(function (shape) {
            if (!shape.getStencil().property(key).readonly()) {
                shape.setProperty(key, value);
                //shape.update();
            }
        }.bind(this));

        /* Propagate changed properties */
        var selectedElements = this.shapeSelection.shapes;

        this.facade.raiseEvent({
            type: ORYX.CONFIG.EVENT_PROPWINDOW_PROP_CHANGED,
            elements: selectedElements,
            key: key,
            value: value
        });

        this.facade.getCanvas().update();

    },

    // if a field becomes invalid after editing the shape must be restored to the old value
    updateAfterInvalid: function (key) {
        this.shapeSelection.shapes.each(function (shape) {
            if (!shape.getStencil().property(key).readonly()) {
                shape.setProperty(key, this.oldValues[shape.getId()]);
                shape.update();
            }
        }.bind(this));

        this.facade.getCanvas().update();
    },

    // extended by Kerstin (start)
    dialogClosed: function (data) {
        var row = this.field ? this.field.row : this.row
        this.scope.afterEdit({
            grid: this.scope.grid,
            record: this.scope.grid.getStore().getAt(row),
            //value:this.scope.grid.getStore().getAt(this.row).get("value")
            value: data
        })
        // reopen the text field of the complex list field again
        this.scope.grid.startEditing(row, this.col);
    },
    // extended by Kerstin (end)

    /**
     * Changes the title of the property window panel according to the selected shapes.
     */
    setPropertyWindowTitle: function () {
        if (this.shapeSelection.shapes.length == 1) {
            // add the name of the stencil of the selected shape to the title
            region.setTitle(ORYX.I18N.PropertyWindow.title + ' (' + this.shapeSelection.shapes.first().getStencil().title() + ')');
        } else {
            region.setTitle(ORYX.I18N.PropertyWindow.title + ' ('
                + this.shapeSelection.shapes.length
                + ' '
                + ORYX.I18N.PropertyWindow.selected
                + ')');
        }
    },
    /**
     * Sets this.shapeSelection.commonPropertiesValues.
     * If the value for a common property is not equal for each shape the value
     * is left empty in the property window.
     */
    setCommonPropertiesValues: function () {
        this.shapeSelection.commonPropertiesValues = new Hash();
        this.shapeSelection.commonProperties.each(function (property) {
            var key = property.prefix() + "-" + property.id();
            var emptyValue = false;
            var firstShape = this.shapeSelection.shapes.first();

            this.shapeSelection.shapes.each(function (shape) {
                if (firstShape.properties[key] != shape.properties[key]) {
                    emptyValue = true;
                }
            }.bind(this));

            /* Set property value */
            if (!emptyValue) {
                this.shapeSelection.commonPropertiesValues[key]
                    = firstShape.properties[key];
            }
        }.bind(this));
    },

    /**
     * Returns the set of stencils used by the passed shapes.
     */
    getStencilSetOfSelection: function () {
        var stencils = new Hash();

        this.shapeSelection.shapes.each(function (shape) {
            stencils[shape.getStencil().id()] = shape.getStencil();
        })
        return stencils;
    },

    /**
     * Identifies the common Properties of the selected shapes.
     */
    identifyCommonProperties: function () {
        this.shapeSelection.commonProperties.clear();
        var stencils = this.getStencilSetOfSelection();
        var firstStencil = stencils.values().first();//one/first selection allowed
        this.shapeSelection.commonProperties = firstStencil.properties();
    },

    onSelectionChanged: function (event) {
        /* Event to call afterEdit method */
        //this.grid.stopEditing();
        if (this.disRegardEvent())
            return;

        /* Selected shapes */
        this.shapeSelection.shapes = event.elements;

        /* Case: nothing selected */
        if (event.elements.length == 0) {
            this.properties = [];
            this.dataSource.loadData(this.properties);
            return;
            //this.shapeSelection.shapes = [this.facade.getCanvas()];
        }

        /* subselection available */
        if (event.subSelection) {
            this.shapeSelection.shapes = [event.subSelection];
        }

        this.setPropertyWindowTitle();
        this.identifyCommonProperties();
        this.setCommonPropertiesValues();

        // Create the Properties

        this.createProperties();
    },

    /**
     * Creates the properties for the ExtJS-Grid from the properties of the
     * selected shapes.
     */
    createProperties: function () {
        this.properties = [];
        this.categoryProperties = [];

        if (this.shapeSelection.commonProperties) {

            // add new property lines
            this.shapeSelection.commonProperties.each((function (pair, index) {

                var key = pair.prefix() + "-" + pair.id();

                // Get the property pair
                var name = pair.title();
                var icons = [];
                var attribute = this.shapeSelection.commonPropertiesValues[key];

                var editorGrid = undefined;
                var editorRenderer = null;

                var refToViewFlag = false;

                if (!pair.readonly()) {
                    switch (pair.type()) {
                        case ORYX.CONFIG.TYPE_STRING:
                            // If the Text is MultiLine
                            if (pair.wrapLines()) {
                                // Set the Editor as TextArea
                                var editorTextArea = new Ext.form.TextArea({alignment: "tl-tl", allowBlank: pair.optional(), msgTarget: 'title', maxLength: pair.length()});
                                editorTextArea.on('keyup', function (textArea, event) {
                                    this.editDirectly(key, textArea.getValue());
                                }.bind(this));

                                editorGrid = new Ext.Editor(editorTextArea);
                            } else {
                                // If not, set the Editor as InputField
                                var editorInput = new Ext.form.TextField({allowBlank: pair.optional(), msgTarget: 'title', maxLength: pair.length()});
                                editorInput.on('keyup', function (input, event) {
                                    this.editDirectly(key, input.getValue());
                                }.bind(this));

                                // reverts the shape if the editor field is invalid
                                editorInput.on('blur', function (input) {
                                    if (!input.isValid(false))
                                        this.updateAfterInvalid(key);
                                }.bind(this));

                                editorInput.on("specialkey", function (input, e) {
                                    if (!input.isValid(false))
                                        this.updateAfterInvalid(key);
                                }.bind(this));

                                editorGrid = new Ext.Editor(editorInput);
                            }
                            break;
                        case ORYX.CONFIG.TYPE_BOOLEAN:
                            // Set the Editor as a CheckBox
                            var editorCheckbox = new Ext.form.Checkbox();
                            editorCheckbox.on('check', function (c, checked) {
                                this.editDirectly(key, checked);
                            }.bind(this));

                            editorGrid = new Ext.Editor(editorCheckbox);
                            break;
                        case ORYX.CONFIG.TYPE_INTEGER:
                            // Set as an Editor for Integers
                            var numberField = new Ext.form.NumberField({allowBlank: pair.optional(), allowDecimals: false, msgTarget: 'title', minValue: pair.min(), maxValue: pair.max()});
                            numberField.on('keyup', function (input, event) {
                                this.editDirectly(key, input.getValue());
                            }.bind(this));

                            editorGrid = new Ext.Editor(numberField);
                            break;
                        case ORYX.CONFIG.TYPE_FLOAT:
                            // Set as an Editor for Float
                            var numberField = new Ext.form.NumberField({ allowBlank: pair.optional(), allowDecimals: true, msgTarget: 'title', minValue: pair.min(), maxValue: pair.max()});
                            numberField.on('keyup', function (input, event) {
                                this.editDirectly(key, input.getValue());
                            }.bind(this));

                            editorGrid = new Ext.Editor(numberField);

                            break;
                        case ORYX.CONFIG.TYPE_COLOR:
                            // Set as a ColorPicker
                            // Ext1.0 editorGrid = new gEdit(new form.ColorField({ allowBlank: pair.optional(),  msgTarget:'title' }));

                            var editorPicker = new Ext.ux.ColorField({ allowBlank: pair.optional(), msgTarget: 'title', facade: this.facade });

                            /*this.facade.registerOnEvent(ORYX.CONFIG.EVENT_COLOR_CHANGE, function(option) {
                             this.editDirectly(key, option.value);
                             }.bind(this));*/

                            editorGrid = new Ext.Editor(editorPicker);

                            break;
                        case ORYX.CONFIG.TYPE_CHOICE:
                            var items = pair.items();
                            if (console) {
                                console.log(pair);
                            }
                            var options = [];
                            items.each(function (value) {
                                if (value.value() == attribute)
                                    attribute = value.title();

                                if (value.refToView()[0])
                                    refToViewFlag = true;

                                options.push([value.icon(), value.title(), value.value()]);

                                icons.push({
                                    name: value.title(),
                                    icon: value.icon()
                                });
                            });

                            var store = new Ext.data.SimpleStore({
                                fields: [
                                    {name: 'icon'},
                                    {name: 'title'},
                                    {name: 'value'}
                                ],
                                data: options // from states.js
                            });

                            // Set the grid Editor

                            var editorCombo = new Ext.form.ComboBox({
                                tpl: '<tpl for="."><div class="x-combo-list-item">{[(values.icon) ? "<img src=\'" + values.icon + "\' />" : ""]} {title}</div></tpl>',
                                store: store,
                                displayField: 'title',
                                valueField: 'value',
                                typeAhead: true,
                                mode: 'local',
                                triggerAction: 'all',
                                selectOnFocus: true
                            });

                            editorCombo.on('select', function (combo, record, index) {
                                this.editDirectly(key, combo.getValue());
                            }.bind(this))

                            editorGrid = new Ext.Editor(editorCombo);

                            break;
                        case ORYX.CONFIG.TYPE_DATE:
                            var currFormat = ORYX.I18N.PropertyWindow.dateFormat
                            if (!(attribute instanceof Date))
                                attribute = Date.parseDate(attribute, currFormat)
                            editorGrid = new Ext.Editor(new Ext.form.DateField({ allowBlank: pair.optional(), format: currFormat, msgTarget: 'title'}));
                            break;

                        case ORYX.CONFIG.TYPE_TEXT:

                            var cf = new Ext.form.ComplexTextField({
                                allowBlank: pair.optional(),
                                dataSource: this.dataSource,
                                grid: this.grid,
                                row: index,
                                facade: this.facade
                            });
                            cf.on('dialogClosed', this.dialogClosed, {scope: this, row: index, col: 1, field: cf});
                            editorGrid = new Ext.Editor(cf);
                            break;

                        // extended by Kerstin (start)
                        case ORYX.CONFIG.TYPE_COMPLEX:

                            var cf = new Ext.form.ComplexListField({ allowBlank: pair.optional()}, pair.complexItems(), key, this.facade);
                            cf.on('dialogClosed', this.dialogClosed, {scope: this, row: index, col: 1, field: cf});
                            editorGrid = new Ext.Editor(cf);
                            break;
                        // extended by Kerstin (end)

                        // extended by Gerardo (Start)
                        case "CPNString":
                            var editorInput = new Ext.form.TextField(
                                {
                                    allowBlank: pair.optional(),
                                    msgTarget: 'title',
                                    maxLength: pair.length(),
                                    enableKeyEvents: true
                                });

                            editorInput.on('keyup', function (input, event) {
                                this.editDirectly(key, input.getValue());
                                console.log(input.getValue());
                                alert("huhu");
                            }.bind(this));

                            editorGrid = new Ext.Editor(editorInput);
                            break;
                        // extended by Gerardo (End)

                        default:
                            var editorInput = new Ext.form.TextField({ allowBlank: pair.optional(), msgTarget: 'title', maxLength: pair.length(), enableKeyEvents: true});
                            editorInput.on('keyup', function (input, event) {
                                this.editDirectly(key, input.getValue());
                            }.bind(this));

                            editorGrid = new Ext.Editor(editorInput);
                    }


                    // Register Event to enable KeyDown
                    editorGrid.on('beforehide', this.facade.enableEvent.bind(this, ORYX.CONFIG.EVENT_KEYDOWN));
                    editorGrid.on('specialkey', this.specialKeyDown.bind(this));

                } else if (pair.type() === ORYX.CONFIG.TYPE_URL || pair.type() === ORYX.CONFIG.TYPE_DIAGRAM_LINK) {
                    attribute = String(attribute).search("http") !== 0 ? ("http://" + attribute) : attribute;
                    attribute = "<a href='" + attribute + "' target='_blank'>" + attribute.split("://")[1] + "</a>"
                }

                // Push to the properties-array
                if (pair.visible()) {
                    var props_ = [pair.category(), name, attribute, icons, {
                        editor: editorGrid,
                        propId: key,
                        type: pair.type(),
                        tooltip: pair.description(),
                        renderer: editorRenderer
                    }];
                    var select = pair.type();
                    if (select && select === 'select')
                        props_ = props_.concat([
                            'select_icon',
                            'Change '+name+'('+attribute+')',
                            false,
                            'edit_icon',
                            'Edit '+name+'('+attribute+')',
                            false
                        ]);
                    else
                        props_ = props_.concat([
                            undefined,
                            undefined,
                            true,
                            undefined,
                            undefined,
                            true
                        ]);


                    if (!pair.category()) {
                        props_[0]='Other';
                    }
                    this.properties.push(props_);
                }

            }).bind(this));
        }

        this.setProperties();
    },

    hideMoreAttrs: function (panel) {
        // TODO: Implement the case that the canvas has no attributes
        if (this.properties.length <= 0) {
            return
        }

        // collapse the "more attr" group
        this.grid.view.toggleGroup(this.grid.view.getGroupId(this.properties[0][0]), false);

        // prevent the more attributes pane from closing after a attribute has been edited
        this.grid.view.un("refresh", this.hideMoreAttrs, this);
    },

    setProperties: function () {
        //var props = this.popularProperties.concat(this.properties);

        this.dataSource.loadData(this.properties);
    }
}
ORYX.Plugins.ETL.Job.Entry.JobEntryPropertyWindow = Clazz.extend(ORYX.Plugins.ETL.Job.Entry.JobEntryPropertyWindow);