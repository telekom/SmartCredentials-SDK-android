/*
 * Copyright (c) 2021 Telekom Deutschland AG
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

package de.telekom.smartcredentials.pmc

import de.telekom.smartcredentials.core.api.PolicyApi
import de.telekom.smartcredentials.core.pmc.PoliciesCallback
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Alex.Graur@endava.com at 2/10/2021
 */
class PolicyController : PolicyApi<PolicySchemaResponse> {

    private val compositeDisposable = CompositeDisposable()

    override fun fetchPolicies(callback: PoliciesCallback<PolicySchemaResponse>) {
        val policyService = RetrofitClient().createRetrofitClient().create(PolicyService::class.java)
        compositeDisposable.add(policyService.getPolicySchemas()
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            callback.onSuccess(it)
                        },
                        {
                            callback.onFailure()
                        }))
    }
}