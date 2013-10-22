package org.flowframe.etl.pentaho.plugin.job.entries.docrepo.fileget.ui;

import org.flowframe.etl.pentaho.plugin.job.entries.docrepo.fileget.RepoFileGet;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.pentaho.di.core.Const;
import org.pentaho.di.i18n.BaseMessages;
import org.pentaho.di.job.JobMeta;
import org.pentaho.di.job.entry.JobEntryDialogInterface;
import org.pentaho.di.job.entry.JobEntryInterface;
import org.pentaho.di.repository.Repository;
import org.pentaho.di.ui.core.gui.WindowProperty;
import org.pentaho.di.ui.core.widget.LabelText;
import org.pentaho.di.ui.core.widget.LabelTextVar;
import org.pentaho.di.ui.job.dialog.JobDialog;
import org.pentaho.di.ui.job.entry.JobEntryDialog;
import org.pentaho.di.ui.trans.step.BaseStepDialog;

public class RepoFileGetDialog extends JobEntryDialog implements JobEntryDialogInterface {
    private static Class<?> PKG = RepoFileGet.class; // for i18n purposes,
    // needed by Translator2!!
    // $NON-NLS-1$

    private LabelText wName;
    private LabelTextVar wHostname;
    private LabelTextVar wPort;
    private LabelTextVar wTimeout;
    private LabelTextVar wRepositoryId;
    private LabelTextVar wCompanyId;
    private LabelTextVar wFolderId;
    private LabelTextVar wFileEntryId;
    private LabelTextVar wLoginEmail;
    private LabelTextVar wLoginPassword;
    private LabelTextVar wLoginGroupId;
    private LabelTextVar wIsaddresult;
    private LabelTextVar wVariable;

    private Button wOK, wCancel;

    private RepoFileGet jobEntry;

    private Shell shell;

    private SelectionAdapter lsDef;

    private boolean changed;

    public RepoFileGetDialog(Shell parent, JobEntryInterface jobEntry, Repository rep, JobMeta jobMeta) {
        super(parent, jobEntry, rep, jobMeta);
        this.jobEntry = (RepoFileGet) jobEntry;

        if (this.jobEntry.getName() == null)
            this.jobEntry.setName(BaseMessages.getString(PKG, "RepoFileGetDialog.Name.Default"));
    }

    public JobEntryInterface open() {
        Shell parent = getParent();
        Display display = parent.getDisplay();

        shell = new Shell(parent, props.getJobsDialogStyle());
        props.setLook(shell);
        JobDialog.setShellImage(shell, jobEntry);

        ModifyListener lsMod = new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                jobEntry.setChanged();
            }
        };
        changed = jobEntry.hasChanged();

        FormLayout formLayout = new FormLayout();
        formLayout.marginWidth = Const.FORM_MARGIN;
        formLayout.marginHeight = Const.FORM_MARGIN;

        shell.setLayout(formLayout);
        shell.setText(BaseMessages.getString(PKG, "RepoFileGetDialog.Title"));

        int margin = Const.MARGIN;

        // Job entry name line
        wName = new LabelText(shell, BaseMessages.getString(PKG, "RepoFileGetDialog.Name.Label"), BaseMessages.getString(PKG, "RepoFileGetDialog.Name.Tooltip"));
        wName.addModifyListener(lsMod);
        FormData fdName = new FormData();
        fdName.top = new FormAttachment(0, 0);
        fdName.left = new FormAttachment(0, 0);
        fdName.right = new FormAttachment(100, 0);
        wName.setLayoutData(fdName);
        Control lastControl = wName;

        // the server
        //
        wHostname = new LabelTextVar(jobMeta, shell, BaseMessages.getString(PKG, "RepoFileGetDialog.hostname.Label"), BaseMessages.getString(PKG, "RepoFileGetDialog.hostname.Label"));
        props.setLook(wHostname);
        wHostname.addModifyListener(lsMod);
        FormData fdBatchIdSchema = new FormData();
        fdBatchIdSchema.left = new FormAttachment(0, 0);
        fdBatchIdSchema.top = new FormAttachment(lastControl, margin);
        fdBatchIdSchema.right = new FormAttachment(100, 0);
        wHostname.setLayoutData(fdBatchIdSchema);
        lastControl = wHostname;

        // the port
        //
        wPort = new LabelTextVar(jobMeta, shell, BaseMessages.getString(PKG, "RepoFileGetDialog.port.Label"), BaseMessages.getString(PKG, "RepoFileGetDialog.port.Label"));
        props.setLook(wPort);
        wPort.addModifyListener(lsMod);
        FormData fdPort = new FormData();
        fdPort.left = new FormAttachment(0, 0);
        fdPort.top = new FormAttachment(lastControl, margin);
        fdPort.right = new FormAttachment(100, 0);
        wPort.setLayoutData(fdPort);
        lastControl = wPort;

        // the timeout
        //
        wTimeout = new LabelTextVar(jobMeta, shell, BaseMessages.getString(PKG, "RepoFileGetDialog.timeout.Label"), BaseMessages.getString(PKG, "RepoFileGetDialog.timeout.Label"));
        props.setLook(wTimeout);
        wTimeout.addModifyListener(lsMod);
        FormData fdTimeout = new FormData();
        fdTimeout.left = new FormAttachment(0, 0);
        fdTimeout.top = new FormAttachment(lastControl, margin);
        fdTimeout.right = new FormAttachment(100, 0);
        wTimeout.setLayoutData(fdTimeout);
        lastControl = wTimeout;

        // the repositoryId
        //
        wRepositoryId = new LabelTextVar(jobMeta, shell, BaseMessages.getString(PKG, "RepoFileGetDialog.repositoryId.Label"), BaseMessages.getString(PKG, "RepoFileGetDialog.repositoryId.Label"));
        props.setLook(wRepositoryId);
        wRepositoryId.addModifyListener(lsMod);
        FormData fdRepositoryId = new FormData();
        fdRepositoryId.left = new FormAttachment(0, 0);
        fdRepositoryId.top = new FormAttachment(lastControl, margin);
        fdRepositoryId.right = new FormAttachment(100, 0);
        wRepositoryId.setLayoutData(fdRepositoryId);
        lastControl = wRepositoryId;

        // the companyId
        //
        wCompanyId = new LabelTextVar(jobMeta, shell, BaseMessages.getString(PKG, "RepoFileGetDialog.companyId.Label"), BaseMessages.getString(PKG, "RepoFileGetDialog.companyId.Label"));
        props.setLook(wCompanyId);
        wCompanyId.addModifyListener(lsMod);
        FormData fdCompanyId = new FormData();
        fdCompanyId.left = new FormAttachment(0, 0);
        fdCompanyId.top = new FormAttachment(lastControl, margin);
        fdCompanyId.right = new FormAttachment(100, 0);
        wCompanyId.setLayoutData(fdCompanyId);
        lastControl = wCompanyId;

        // the folderId
        //
        wFolderId = new LabelTextVar(jobMeta, shell, BaseMessages.getString(PKG, "RepoFileGetDialog.folderId.Label"), BaseMessages.getString(PKG, "RepoFileGetDialog.folderId.Label"));
        props.setLook(wFolderId);
        wFolderId.addModifyListener(lsMod);
        FormData fdFolderId = new FormData();
        fdFolderId.left = new FormAttachment(0, 0);
        fdFolderId.top = new FormAttachment(lastControl, margin);
        fdFolderId.right = new FormAttachment(100, 0);
        wFolderId.setLayoutData(fdFolderId);
        lastControl = wFolderId;        

        // the fileEntryId
        //
        wFileEntryId = new LabelTextVar(jobMeta, shell, BaseMessages.getString(PKG, "RepoFileGetDialog.fileEntryId.Label"), BaseMessages.getString(PKG, "RepoFileGetDialog.fileEntryId.Label"));
        props.setLook(wFileEntryId);
        wFileEntryId.addModifyListener(lsMod);
        FormData fdFileEntryId = new FormData();
        fdFileEntryId.left = new FormAttachment(0, 0);
        fdFileEntryId.top = new FormAttachment(lastControl, margin);
        fdFileEntryId.right = new FormAttachment(100, 0);
        wFileEntryId.setLayoutData(fdFileEntryId);
        lastControl = wFileEntryId;

        // the loginEmail
        //
        wLoginEmail = new LabelTextVar(jobMeta, shell, BaseMessages.getString(PKG, "RepoFileGetDialog.loginEmail.Label"), BaseMessages.getString(PKG, "RepoFileGetDialog.loginEmail.Label"));
        props.setLook(wLoginEmail);
        wLoginEmail.addModifyListener(lsMod);
        FormData fdLoginEmail = new FormData();
        fdLoginEmail.left = new FormAttachment(0, 0);
        fdLoginEmail.top = new FormAttachment(lastControl, margin);
        fdLoginEmail.right = new FormAttachment(100, 0);
        wLoginEmail.setLayoutData(fdLoginEmail);
        lastControl = wLoginEmail;


        // the loginPassword
        //
        wLoginPassword = new LabelTextVar(jobMeta, shell, BaseMessages.getString(PKG, "RepoFileGetDialog.loginPassword.Label"), BaseMessages.getString(PKG, "RepoFileGetDialog.loginPassword.Label"));
        props.setLook(wLoginPassword);
        wLoginPassword.addModifyListener(lsMod);
        FormData fdLoginPassword = new FormData();
        fdLoginPassword.left = new FormAttachment(0, 0);
        fdLoginPassword.top = new FormAttachment(lastControl, margin);
        fdLoginPassword.right = new FormAttachment(100, 0);
        wLoginPassword.setLayoutData(fdLoginPassword);
        lastControl = wLoginPassword;

        // the loginGroupId
        //
        wLoginGroupId = new LabelTextVar(jobMeta, shell, BaseMessages.getString(PKG, "RepoFileGetDialog.loginGroupId.Label"), BaseMessages.getString(PKG, "RepoFileGetDialog.loginGroupId.Label"));
        props.setLook(wLoginGroupId);
        wLoginGroupId.addModifyListener(lsMod);
        FormData fdLoginGroupId = new FormData();
        fdLoginGroupId.left = new FormAttachment(0, 0);
        fdLoginGroupId.top = new FormAttachment(lastControl, margin);
        fdLoginGroupId.right = new FormAttachment(100, 0);
        wLoginGroupId.setLayoutData(fdLoginGroupId);
        lastControl = wLoginGroupId;

        // the isaddresult
        //
        wIsaddresult = new LabelTextVar(jobMeta, shell, BaseMessages.getString(PKG, "RepoFileGetDialog.isaddresult.Label"), BaseMessages.getString(PKG, "RepoFileGetDialog.isaddresult.Label"));
        props.setLook(wIsaddresult);
        wIsaddresult.addModifyListener(lsMod);
        FormData fdIsaddresult = new FormData();
        fdIsaddresult.left = new FormAttachment(0, 0);
        fdIsaddresult.top = new FormAttachment(lastControl, margin);
        fdIsaddresult.right = new FormAttachment(100, 0);
        wIsaddresult.setLayoutData(fdIsaddresult);
        lastControl = wIsaddresult;


        // the variable
        //
        wVariable = new LabelTextVar(jobMeta, shell, BaseMessages.getString(PKG, "RepoFileGetDialog.Variable.Label"), BaseMessages.getString(PKG, "RepoFileGetDialog.Variable.Tooltip"));
        props.setLook(wVariable);
        wVariable.addModifyListener(lsMod);
        FormData fdVariable = new FormData();
        fdVariable.left = new FormAttachment(0, 0);
        fdVariable.top = new FormAttachment(lastControl, margin);
        fdVariable.right = new FormAttachment(100, 0);
        wVariable.setLayoutData(fdVariable);
        lastControl = wVariable;

        wOK = new Button(shell, SWT.PUSH);
        wOK.setText(BaseMessages.getString(PKG, "System.Button.OK"));
        wCancel = new Button(shell, SWT.PUSH);
        wCancel.setText(BaseMessages.getString(PKG, "System.Button.Cancel"));

        BaseStepDialog.positionBottomButtons(shell, new Button[]{wOK, wCancel}, margin, lastControl);

        // Add listeners
        //
        wCancel.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event e) {
                cancel();
            }
        });
        wOK.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event e) {
                ok();
            }
        });

        lsDef = new SelectionAdapter() {
            public void widgetDefaultSelected(SelectionEvent e) {
                ok();
            }
        };

        wName.addSelectionListener(lsDef);
        wHostname.addSelectionListener(lsDef);
        wPort.addSelectionListener(lsDef);
        wVariable.addSelectionListener(lsDef);

        // Detect X or ALT-F4 or something that kills this window...
        shell.addShellListener(new ShellAdapter() {
            public void shellClosed(ShellEvent e) {
                cancel();
            }
        });

        getData();

        BaseStepDialog.setSize(shell);

        shell.open();
        props.setDialogSize(shell, "RepoFileGetDialog.DialogSize");
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch())
                display.sleep();
        }
        return jobEntry;
    }

    public void dispose() {
        WindowProperty winprop = new WindowProperty(shell);
        props.setScreen(winprop);
        shell.dispose();
    }

    /**
     * Copy information from the meta-data input to the dialog fields.
     */
    public void getData() {
        wName.setText(Const.NVL(jobEntry.getName(), ""));
        wName.getTextWidget().selectAll();
        wHostname.setText(Const.NVL(jobEntry.getHostname(), ""));
        wPort.setText(Const.NVL(jobEntry.getPort(), ""));
        wVariable.setText(Const.NVL(jobEntry.getVariableName(), ""));
    }

    private void cancel() {
        jobEntry.setChanged(changed);
        jobEntry = null;
        dispose();
    }

    private void ok() {
        jobEntry.setName(wName.getText());

        getInfo(jobEntry);

        dispose();
    }

    private void getInfo(RepoFileGet entry) {
        entry.setHostname(wHostname.getText());
        entry.setPort(wPort.getText());
        entry.setVariableName(wVariable.getText());
    }
}