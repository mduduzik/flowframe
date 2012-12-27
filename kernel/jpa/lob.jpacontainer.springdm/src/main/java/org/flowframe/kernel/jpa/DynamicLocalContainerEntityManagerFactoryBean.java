package org.flowframe.kernel.jpa;

import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.weaving.LoadTimeWeaverAware;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

public class DynamicLocalContainerEntityManagerFactoryBean extends LocalContainerEntityManagerFactoryBean 
	implements ResourceLoaderAware, LoadTimeWeaverAware {

}
