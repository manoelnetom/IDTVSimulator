/*

 This file is part of XleTView
 Copyright (C) 2003 Martin Sveden

 This is free software, and you are
 welcome to redistribute it under
 certain conditions;

 See LICENSE document for details.

*/

package xjavax.tv.service.selection;

import javax.tv.xlet.XletContext;

import br.org.sbtvd.application.XletContextImpl;

/**
 * 
 * 
 * @author Martin Sveden
 * @statuscode 4
 */
public class ServiceContextFactoryImpl extends ServiceContextFactory {

    ServiceContext[] serviceContexts = { ServiceContextImpl.getInstance()};

    public ServiceContextFactoryImpl() {
    }

    public ServiceContext getServiceContext(XletContext xletcontext) throws SecurityException, ServiceContextException {
        
    	if (xletcontext == null){
            
    		throw new NullPointerException("XletContext null");
    		
        }
        
    	ServiceContext servicecontext = (ServiceContext) xletcontext.getXletProperty("javax.tv.xlet.service_context");
        
    	if (servicecontext == null){
            
    		throw new ServiceContextException("xlet not running in a ServiceContext");
    		
    	}
    	
        if (servicecontext instanceof ServiceContextImpl) {
            
        	ServiceContextImpl servicecontextimpl = (ServiceContextImpl) servicecontext;
            
        	if (servicecontextimpl.isDestroyed()){
            
        		throw new ServiceContextException("ServiceContext is destroyed");
            
        	}
        }
        return servicecontext;
    }

    public ServiceContext[] getServiceContexts() {
        return serviceContexts;
    }

    public ServiceContext createServiceContext() throws InsufficientResourcesException, SecurityException {
        return serviceContexts[0];
    }
}
