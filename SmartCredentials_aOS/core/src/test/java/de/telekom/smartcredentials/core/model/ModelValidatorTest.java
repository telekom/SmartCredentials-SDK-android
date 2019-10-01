/*
 * Copyright (c) 2019 Telekom Deutschland AG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.telekom.smartcredentials.core.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import de.telekom.smartcredentials.core.model.item.ItemDomainMetadata;
import de.telekom.smartcredentials.core.model.item.ItemDomainModel;

import static de.telekom.smartcredentials.core.model.ModelValidator.NO_METADATA_EXCEPTION_MSG;
import static de.telekom.smartcredentials.core.model.ModelValidator.NULL_PARAMETER_EXCEPTION_MSG;

public class ModelValidatorTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void checkParamNotNullThrowsDomainModelExceptionWhenItemDomainModelIsNull() {
        thrown.expect(DomainModelException.class);
        thrown.expectMessage(NULL_PARAMETER_EXCEPTION_MSG);

        ModelValidator.checkParamNotNull((ItemDomainModel) null);
    }

    @Test
    public void checkParamNotNullRunsWithoutExceptionsWhenItemDomainModelIsValid() {
        ModelValidator.checkParamNotNull(new ItemDomainModel());
    }

    @Test
    public void checkParamNotNullThrowsDomainModelExceptionWhenItemDomainMetadataIsNull() {
        thrown.expect(DomainModelException.class);
        thrown.expectMessage(NO_METADATA_EXCEPTION_MSG);

        ModelValidator.checkParamNotNull((ItemDomainMetadata) null);
    }

    @Test
    public void checkParamNotNullRunsWithoutExceptionsWhenItemDomainMetadataIsValid() {
        ModelValidator.checkParamNotNull(new ItemDomainMetadata(true));
    }

    @Test
    public void getValidatedMetadataThrowsExceptionWhenItemDomainModelIsNull() {
        thrown.expect(DomainModelException.class);
        thrown.expectMessage(NULL_PARAMETER_EXCEPTION_MSG);

        ModelValidator.getValidatedMetadata(null);
    }

    @Test
    public void getValidatedMetadataThrowsExceptionWhenItemMetadataModelIsNull() {
        thrown.expect(DomainModelException.class);
        thrown.expectMessage(NO_METADATA_EXCEPTION_MSG);

        ModelValidator.getValidatedMetadata(new ItemDomainModel());
    }

    @Test
    public void getValidatedMetadataRunsSuccessfullyWhenModelIsValid() {
        ModelValidator.getValidatedMetadata(new ItemDomainModel().setMetadata(new ItemDomainMetadata(true)));
    }

}