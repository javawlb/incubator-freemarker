/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package freemarker.core;

import java.io.IOException;

import freemarker.template.TemplateException;
import freemarker.template.utility.StandardCompress;

/**
 * An instruction that reduces all sequences of whitespace to a single
 * space or newline. In addition, leading and trailing whitespace is removed.
 * @see freemarker.template.utility.StandardCompress
 */
final class CompressedBlock extends TemplateElement {

    CompressedBlock(TemplateElement nestedBlock) { 
        setNestedBlock(nestedBlock);
    }

    @Override
    TemplateElement[] accept(Environment env) throws TemplateException, IOException {
        if (getNestedBlock() != null) {
            env.visitAndTransform(getNestedBlock(), StandardCompress.INSTANCE, null);
        }
        return null;
    }

    @Override
    protected String dump(boolean canonical) {
        if (canonical) {
            String nested = getNestedBlock() != null ? getNestedBlock().getCanonicalForm() : "";
            return "<" + getNodeTypeSymbol() + ">" + nested + "</" + getNodeTypeSymbol() + ">";
        } else {
            return getNodeTypeSymbol();
        }
    }
    
    @Override
    String getNodeTypeSymbol() {
        return "#compress";
    }
    
    @Override
    int getParameterCount() {
        return 0;
    }

    @Override
    Object getParameterValue(int idx) {
        throw new IndexOutOfBoundsException();
    }

    @Override
    ParameterRole getParameterRole(int idx) {
        throw new IndexOutOfBoundsException();
    }

    @Override
    boolean isIgnorable() {
        return getNestedBlock() == null || getNestedBlock().isIgnorable();
    }

    @Override
    boolean isNestedBlockRepeater() {
        return false;
    }
    
}
