/*
 *   Copyright (c) 2015, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *   WSO2 Inc. licenses this file to you under the Apache License,
 *   Version 2.0 (the "License"); you may not use this file except
 *   in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing,
 *   software distributed under the License is distributed on an
 *   "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *   KIND, either express or implied.  See the License for the
 *   specific language governing permissions and limitations
 *   under the License.
 *
 */
package org.wso2.carbon.device.mgt.core.api.mgt;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.apimgt.api.APIManagementException;
import org.wso2.carbon.apimgt.api.APIProvider;
import org.wso2.carbon.apimgt.api.model.API;
import org.wso2.carbon.apimgt.api.model.APIIdentifier;
import org.wso2.carbon.apimgt.impl.APIManagerFactory;

import java.util.List;

public class APIPublisherServiceImpl implements APIPublisherService {

    private static final Log log = LogFactory.getLog(APIPublisherServiceImpl.class);

    @Override
    public void publishAPI(API api) throws APIManagementException {
        if (log.isDebugEnabled()) {
            log.debug("Publishing API '" + api.getId() + "'");
        }
        APIProvider provider = APIManagerFactory.getInstance().getAPIProvider(api.getApiOwner());
        provider.addAPI(api);
        if (log.isDebugEnabled()) {
            log.debug("Successfully published API '" + api.getId() + "' with the context '" +
                    api.getContext() + "'");
        }
    }

    @Override
    public void removeAPI(APIIdentifier id) throws APIManagementException {
        if (log.isDebugEnabled()) {
            log.debug("Removing API '" + id.getApiName() + "'");
        }
        APIProvider provider = APIManagerFactory.getInstance().getAPIProvider(id.getProviderName());
        provider.deleteAPI(id);
        if (log.isDebugEnabled()) {
            log.debug("API '" + id.getApiName() + "' has been successfully removed");
        }
    }

    @Override
    public void publishAPIs(List<API> apis) throws APIManagementException {
        if (log.isDebugEnabled()) {
            log.debug("Publishing a batch of APIs");
        }
        for (API api : apis) {
            this.publishAPI(api);
        }
        if (log.isDebugEnabled()) {
            log.debug("Successfully published the batch of APIs");
        }
    }

}
