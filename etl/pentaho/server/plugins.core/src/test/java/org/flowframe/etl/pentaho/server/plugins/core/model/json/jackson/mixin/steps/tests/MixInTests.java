package org.flowframe.etl.pentaho.server.plugins.core.model.json.jackson.mixin.steps.tests;

import junit.framework.Assert;
import org.codehaus.jackson.map.ObjectMapper;
import org.flowframe.etl.pentaho.server.plugins.core.model.json.CustomObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.pentaho.di.core.RowMetaAndData;
import org.pentaho.di.core.row.RowMeta;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.core.row.ValueMeta;
import org.pentaho.di.core.row.ValueMetaInterface;
import org.pentaho.di.trans.steps.textfileinput.TextFileInputMeta;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;


public class MixInTests {
    private ObjectMapper mapper;

    /**
	 * @throws Exception
	 */
	@Before
	public  void setUpBefore() throws Exception {
        mapper = new CustomObjectMapper();
	}

	/**
	 * @throws Exception
	 */
	@After
	public void tearDownAfter() throws Exception {

	}


    @Ignore
	@Test
	public final void testTextFileInputMetaMixIn() throws Exception {
        assertNotNull(mapper);

        TextFileInputMeta meta = new TextFileInputMeta();
        meta.setDefault();
        String res = mapper.writeValueAsString(meta);
        assertNotNull(res);

        meta = mapper.readValue(res, TextFileInputMeta.class);
        Assert.assertNotNull(meta);
	}

    @Ignore
    @Test
    public void testRowMetaAndDataMixIn() throws Exception {
        assertNotNull(mapper);

        List<RowMetaAndData> data = createData();
        String res = mapper.writeValueAsString(data);
        assertNotNull(res);
    }

    public List<RowMetaAndData> createData()
    {
        List<RowMetaAndData> list = new ArrayList<RowMetaAndData>();

        RowMetaInterface rm = createRowMetaInterface();

        Object[] r1 = new Object[] { "abc" };
        Object[] r2 = new Object[] { "ABC" };
        Object[] r3 = new Object[] { "abc" };
        Object[] r4 = new Object[] { "ABC" };

        list.add(new RowMetaAndData(rm, r1));
        list.add(new RowMetaAndData(rm, r2));
        list.add(new RowMetaAndData(rm, r3));
        list.add(new RowMetaAndData(rm, r4));

        return list;
    }

    public RowMetaInterface createRowMetaInterface()
    {
        RowMetaInterface rm = new RowMeta();

        ValueMetaInterface valuesMeta[] = {
                new ValueMeta("KEY", ValueMeta.TYPE_STRING),
        };

        for (int i=0; i < valuesMeta.length; i++ )
        {
            rm.addValueMeta(valuesMeta[i]);
        }

        return rm;
    }
}
