/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2011, Red Hat, Inc., and individual contributors
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

package org.jboss.as.controller;

import org.jboss.dmr.ModelNode;

/**
 * Handler for an individual step in the overall execution of a management operation.
 * <p>
 * A handler is associated with a single {@link org.jboss.as.controller.OperationContext.Stage stage} of operation execution
 * and should only perform functions appropriate for that stage.
 * <p>
 * A handler {@link org.jboss.as.controller.registry.ManagementResourceRegistration#registerOperationHandler(OperationDefinition, OperationStepHandler) registered with a ManagementResourceRegistration}
 * will execute in {@link org.jboss.as.controller.OperationContext.Stage#MODEL}; otherwise the step will execute in the
 * stage passed to {@link org.jboss.as.controller.OperationContext#addStep(OperationStepHandler, org.jboss.as.controller.OperationContext.Stage)}
 * when it was added.
 *
 * @author <a href="mailto:david.lloyd@redhat.com">David M. Lloyd</a>
 */
@FunctionalInterface
public interface OperationStepHandler {

    /**
     * Execute this step.  If the operation fails, {@link OperationContext#getFailureDescription() context.getFailureDescription()}
     * must be called, or an {@link OperationFailedException} must be thrown.
     * If the operation succeeded and the operation provides a return value, {@link OperationContext#getResult() context.getResult()} should
     * be called and the result populated with the outcome. If the handler wishes to take further action once the result
     * of the overall operation execution is known, one of the
     * {@link org.jboss.as.controller.OperationContext#completeStep(OperationContext.ResultHandler) context.completeStep variants}
     * should be called to register a callback. The callback will not be invoked if this method throws an exception.
     * <p>When this method is invoked the {@link Thread#getContextClassLoader() thread context classloader} will
     * be set to be the defining class loader of the class that implements this interface.</p>
     *
     * @param context the operation context
     * @param operation the operation being executed
     * @throws OperationFailedException if the operation failed <b>before</b> calling {@code context.completeStep()}
     */
    void execute(OperationContext context, ModelNode operation) throws OperationFailedException;
}
