/*
 *   Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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
package org.wso2.carbon.device.mgt.jaxrs.service.api.admin;

import io.swagger.annotations.*;
import org.wso2.carbon.apimgt.annotations.api.API;
import org.wso2.carbon.apimgt.annotations.api.Permission;
import org.wso2.carbon.device.mgt.common.Device;
import org.wso2.carbon.device.mgt.jaxrs.beans.ErrorResponse;

import javax.validation.constraints.Size;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@API(name = "DeviceManagementAdmin", version = "1.0.0", context = "/api/device-mgt/v1.0/admin/devices",
        tags = {"device_management"})
@Path("/admin/devices")
@Api(value = "Device Management Administrative Service", description = "This an  API intended to be used by " +
        "'internal' components to log in as an admin user and do a selected number of operations. " +
        "Further, this is strictly restricted to admin users only ")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface DeviceManagementAdminService {

    @GET
    @ApiOperation(
            produces = MediaType.APPLICATION_JSON,
            httpMethod = "GET",
            value = "Getting Details of a Device",
            notes = "Get the details of a device by searching via the device name, device type and the tenant domain.",
            response = Device.class,
            responseContainer = "List",
            tags = "Device Management Administrative Service")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. \n Successfully fetched the list of devices.",
                    response = Device.class,
                    responseContainer = "List",
                    responseHeaders = {
                            @ResponseHeader(
                                    name = "Content-Type",
                                    description = "The content type of the body"),
                            @ResponseHeader(
                                    name = "ETag",
                                    description = "Entity Tag of the response resource.\n" +
                                            "Used by caches, or in conditional requests."),
                            @ResponseHeader(
                                    name = "Last-Modified",
                                    description = "Date and time the resource was last modified.\n" +
                                            "Used by caches, or in conditional requests."),
                    }),
            @ApiResponse(
                    code = 304,
                    message = "Not Modified. Empty body because the client already has the latest version of the requested resource.\n"),
            @ApiResponse(
                    code = 401,
                    message = "Unauthorized.\n The unauthorized access to the requested resource.",
                    response = ErrorResponse.class),
            @ApiResponse(
                    code = 404,
                    message = "Not Found.\n The specified device does not exist",
                    response = ErrorResponse.class),
            @ApiResponse(
                    code = 406,
                    message = "Not Acceptable.\n The requested media type is not supported"),
            @ApiResponse(
                    code = 500,
                    message = "Internal Server Error. \n Server error occurred while fetching the device list.",
                    response = ErrorResponse.class)
    })
    @Permission(name = "View Devices", permission = "/device-mgt/devices/owning-device/view")
    Response getDevicesByName(
            @ApiParam(
                    name = "name",
                    value = "The name of the device.If you are unsure of the name of the device, run the GET /devices API that is under Device Management.",
                    required = true)
            @QueryParam("name")
            @Size(max = 45)
            String name,
            @ApiParam(
                    name = "type",
                    value = "The type of the device, such as android, ios or windows.",
                    required = true,
                    allowableValues = "android, ios, windows")
            @QueryParam("type")
            @Size(min = 2, max = 45)
            String type,
            @ApiParam(
                    name = "tenant-domain",
                    value = "The name of the tenant.\n" +
                            "The default tenant domain of WSO2 EMM is carbon.super",
                    required = true,
                    defaultValue = "carbon.super")
            @QueryParam("tenant-domain") String tenantDomain,
            @ApiParam(
                    name = "If-Modified-Since",
                    value = "Checks if the requested variant was modified, since the specified date-time. \n" +
                            "Provide the value in the following format: EEE, d MMM yyyy HH:mm:ss Z. \n" +
                            "Example: Mon, 05 Jan 2014 15:10:00 +0200",
                    required = false)
            @HeaderParam("If-Modified-Since") String ifModifiedSince,
            @ApiParam(
                    name = "offset",
                    value = "The starting pagination index for the complete list of qualified items.",
                    required = false,
                    defaultValue = "0")
            @QueryParam("offset") int offset,
            @ApiParam(
                    name = "limit",
                    value = "Provide how many activity details you require from the starting pagination index/offset.",
                    required = false,
                    defaultValue = "5")
            @QueryParam("limit") int limit);

}
