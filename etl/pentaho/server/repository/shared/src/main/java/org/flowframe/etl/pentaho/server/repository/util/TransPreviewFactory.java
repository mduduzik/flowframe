package org.flowframe.etl.pentaho.server.repository.util;

import org.pentaho.di.core.plugins.PluginRegistry;
import org.pentaho.di.core.plugins.StepPluginType;
import org.pentaho.di.core.variables.VariableSpace;
import org.pentaho.di.trans.TransHopMeta;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.step.StepMetaInterface;
import org.pentaho.di.trans.steps.dummytrans.DummyTransMeta;

/**
 * Created with IntelliJ IDEA.
 * User: Mduduzi
 * Date: 8/26/13
 * Time: 11:02 AM
 * To change this template use File | Settings | File Templates.
 */
public class TransPreviewFactory {
    public static final TransMeta generatePreviewTransformation(VariableSpace parent, StepMetaInterface oneMeta, String oneStepname)
    {
        PluginRegistry registry = PluginRegistry.getInstance();

        TransMeta previewMeta = new TransMeta(parent);
        // The following operation resets the internal variables!
        //
        previewMeta.setName(parent==null ? "Preview transformation" : parent.toString());

        // At it to the first step.
        StepMeta one = new StepMeta(registry.getPluginId(StepPluginType.class, oneMeta), oneStepname, oneMeta);
        //one.setLocation(50,50);
        one.setDraw(false);
        previewMeta.addStep(one);

        DummyTransMeta twoMeta = new DummyTransMeta();
        StepMeta two = new StepMeta(registry.getPluginId(StepPluginType.class, twoMeta), "dummy", twoMeta); //$NON-NLS-1$
        //two.setLocation(250,50);
        two.setDraw(false);
        previewMeta.addStep(two);

        TransHopMeta hop = new TransHopMeta(one, two);
        previewMeta.addTransHop(hop);

        return previewMeta;
    }
}
