/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.server.integrity.model;

import android.annotation.Nullable;
import android.content.integrity.Rule;
import android.util.StatsLog;

/**
 * A class encapsulating the result from the evaluation engine after evaluating rules against app
 * install metadata.
 *
 * <p>It contains the outcome effect (whether to allow or block the install), and the rule causing
 * that effect.
 */
public final class IntegrityCheckResult {

    public enum Effect {
        ALLOW,
        DENY
    }

    private final Effect mEffect;
    @Nullable private final Rule mRule;

    private IntegrityCheckResult(Effect effect, @Nullable Rule rule) {
        this.mEffect = effect;
        this.mRule = rule;
    }

    public Effect getEffect() {
        return mEffect;
    }

    public Rule getRule() {
        return mRule;
    }

    /**
     * Create an ALLOW evaluation outcome.
     *
     * @return An evaluation outcome with ALLOW effect and no rule.
     */
    public static IntegrityCheckResult allow() {
        return new IntegrityCheckResult(Effect.ALLOW, null);
    }

    /**
     * Create an ALLOW evaluation outcome.
     *
     * @return An evaluation outcome with ALLOW effect and rule causing that effect.
     */
    public static IntegrityCheckResult allow(Rule rule) {
        return new IntegrityCheckResult(Effect.ALLOW, rule);
    }

    /**
     * Create a DENY evaluation outcome.
     *
     * @param rule Rule causing the DENY effect.
     * @return An evaluation outcome with DENY effect and rule causing that effect.
     */
    public static IntegrityCheckResult deny(Rule rule) {
        return new IntegrityCheckResult(Effect.DENY, rule);
    }

    /**
     * Returns the in value of the integrity check result for logging purposes.
     */
    public int getLoggingResponse() {
        if (getEffect() == IntegrityCheckResult.Effect.DENY) {
            return StatsLog.INTEGRITY_CHECK_RESULT_REPORTED__RESPONSE__REJECTED;
        } else if (getRule() != null) {
            return StatsLog.INTEGRITY_CHECK_RESULT_REPORTED__RESPONSE__FORCE_ALLOWED;
        } else {
            return StatsLog.INTEGRITY_CHECK_RESULT_REPORTED__RESPONSE__ALLOWED;
        }
    }

    /**
     * Returns true when the {@code Effect.DENY} result is caused by an app certificate mismatch.
     */
    public boolean isCausedByAppCertRule() {
        // TODO(b/147095027): implement this.
        return true;
    }

    /**
     * Returns true when the {@code Effect.DENY} result is caused by an installer rule.
     */
    public boolean isCausedByInstallerRule() {
        // TODO(b/147095027): implement this.
        return true;
    }

}
