/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2011-2012 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * http://glassfish.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */
package org.glassfish.jersey.server.model;

import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.process.Inflector;

/**
 * Models a dynamically created resource method. Such a method is defined with
 * a provided {@link Inflector} instance. The Inflector should consume the incoming
 * request and provide a response back.
 *
 * @author Jakub Podlesak (jakub.podlesak at oracle.com)
 */
public class InflectorBasedResourceMethod extends AbstractResourceMethod {

    private Inflector<Request, Response> inflector;

    /**
     * Constructs a new instance bound to the given resource class.
     * You need to manually add the new instance to the list of existing resource methods.
     *
     * @see Inflector
     *
     * @param resource where this new method should be bound
     * @param httpMethod (e.g. "GET", "PUT", ...)
     * @param inputMediaTypes non-null list of supported input media types, should be empty if media types are not specified explicitly
     * @param outputMediaTypes non-null list of supported output media types, should be empty if media types are not specified explicitly
     * @param inflector
     */
    public InflectorBasedResourceMethod(ResourceClass resource, String httpMethod,
            List<MediaType> inputMediaTypes, List<MediaType> outputMediaTypes, Inflector<Request, Response> inflector) {

        super(resource, httpMethod);
        this.inflector = inflector;

        setAreInputTypesDeclared(!inputMediaTypes.isEmpty());
        getSupportedInputTypes().addAll(inputMediaTypes);

        setAreOutputTypesDeclared(!outputMediaTypes.isEmpty());
        getSupportedOutputTypes().addAll(outputMediaTypes);
    }


    /**
     * Getter for inflector.
     *
     * @return encapsulated inflector
     */
    public Inflector<Request, Response> getInflector() {
        return inflector;
    }

    // ResourceModelComponent
    @Override
    public void accept(ResourceModelVisitor visitor) {
        visitor.visitInflectorResourceMethod(this);
    }
}
