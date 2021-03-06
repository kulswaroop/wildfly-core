/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2012, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jboss.as.domain.management.security;

import org.jboss.as.controller.ModelVersion;
import org.jboss.as.controller.descriptions.common.ControllerResolver;
import org.jboss.as.domain.management.ModelDescriptionConstants;
import org.jboss.as.controller.AttributeDefinition;
import org.jboss.as.controller.PathElement;
import org.jboss.as.controller.SimpleAttributeDefinition;
import org.jboss.as.controller.SimpleAttributeDefinitionBuilder;
import org.jboss.as.controller.SimpleResourceDefinition;
import org.jboss.as.controller.operations.validation.StringLengthValidator;
import org.jboss.as.controller.registry.AttributeAccess;
import org.jboss.as.controller.registry.ManagementResourceRegistration;
import org.jboss.as.controller.registry.OperationEntry;
import org.jboss.dmr.ModelNode;
import org.jboss.dmr.ModelType;

/**
 * {@link org.jboss.as.controller.ResourceDefinition} for a security realm's local authentication resource.
 *
 * @author <a href="mailto:darran.lofthouse@jboss.com">Darran Lofthouse</a>
 */
public class LocalAuthenticationResourceDefinition extends SimpleResourceDefinition {

    public static final SimpleAttributeDefinition DEFAULT_USER = new SimpleAttributeDefinitionBuilder(ModelDescriptionConstants.DEFAULT_USER, ModelType.STRING)
            .setValidator(new StringLengthValidator(1, Integer.MAX_VALUE, true, true))
            .setRequired(false)
            .setAllowExpression(true)
            .setFlags(AttributeAccess.Flag.RESTART_RESOURCE_SERVICES)
            .build();

    public static final SimpleAttributeDefinition ALLOWED_USERS = new SimpleAttributeDefinitionBuilder(ModelDescriptionConstants.ALLOWED_USERS, ModelType.STRING)
            .setValidator(new StringLengthValidator(1, Integer.MAX_VALUE, true, true))
            .setRequired(false)
            .setAllowExpression(true)
            .setFlags(AttributeAccess.Flag.RESTART_RESOURCE_SERVICES).build();

    public static final SimpleAttributeDefinition SKIP_GROUP_LOADING = new SimpleAttributeDefinitionBuilder(ModelDescriptionConstants.SKIP_GROUP_LOADING, ModelType.BOOLEAN)
            .setDefaultValue(new ModelNode(false))
            .setRequired(false)
            .setAllowExpression(true)
            .setFlags(AttributeAccess.Flag.RESTART_RESOURCE_SERVICES)
            .build();

    public static final AttributeDefinition[] ATTRIBUTE_DEFINITIONS = { DEFAULT_USER, ALLOWED_USERS, SKIP_GROUP_LOADING };

    public LocalAuthenticationResourceDefinition() {
        super(new Parameters(PathElement.pathElement(ModelDescriptionConstants.AUTHENTICATION, ModelDescriptionConstants.LOCAL),
                ControllerResolver.getDeprecatedResolver(SecurityRealmResourceDefinition.DEPRECATED_PARENT_CATEGORY,
                        "core.management.security-realm.authentication.local"))
                .setAddHandler(new SecurityRealmChildAddHandler(true, false, ATTRIBUTE_DEFINITIONS))
                .setRemoveHandler(new SecurityRealmChildRemoveHandler(true))
                .setAddRestartLevel(OperationEntry.Flag.RESTART_ALL_SERVICES)
                .setRemoveRestartLevel(OperationEntry.Flag.RESTART_ALL_SERVICES)
                .setDeprecatedSince(ModelVersion.create(1, 7)));
    }

    @Override
    public void registerAttributes(ManagementResourceRegistration resourceRegistration) {
        SecurityRealmChildWriteAttributeHandler handler = new SecurityRealmChildWriteAttributeHandler(ATTRIBUTE_DEFINITIONS);
        handler.registerAttributes(resourceRegistration);
    }

}
