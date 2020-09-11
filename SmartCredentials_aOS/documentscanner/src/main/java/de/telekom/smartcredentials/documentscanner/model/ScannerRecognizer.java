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

/*
 * Created by Lucian Iacob on 6/29/18 2:09 PM.
 * lucian.iacob@endava.com
 * Deutsche Telekom - All Rights Reserved - ©
 */

package de.telekom.smartcredentials.documentscanner.model;

import androidx.annotation.NonNull;

import com.microblink.entities.recognizers.Recognizer;
import com.microblink.entities.recognizers.blinkid.austria.AustriaCombinedRecognizer;
import com.microblink.entities.recognizers.blinkid.austria.AustriaIdBackRecognizer;
import com.microblink.entities.recognizers.blinkid.austria.AustriaIdFrontRecognizer;
import com.microblink.entities.recognizers.blinkid.brunei.BruneiIdBackRecognizer;
import com.microblink.entities.recognizers.blinkid.brunei.BruneiIdFrontRecognizer;
import com.microblink.entities.recognizers.blinkid.colombia.ColombiaIdBackRecognizer;
import com.microblink.entities.recognizers.blinkid.colombia.ColombiaIdFrontRecognizer;
import com.microblink.entities.recognizers.blinkid.croatia.CroatiaCombinedRecognizer;
import com.microblink.entities.recognizers.blinkid.croatia.CroatiaIdBackRecognizer;
import com.microblink.entities.recognizers.blinkid.croatia.CroatiaIdFrontRecognizer;
import com.microblink.entities.recognizers.blinkid.cyprus.CyprusIdBackRecognizer;
import com.microblink.entities.recognizers.blinkid.cyprus.CyprusIdFrontRecognizer;
import com.microblink.entities.recognizers.blinkid.czechia.CzechiaCombinedRecognizer;
import com.microblink.entities.recognizers.blinkid.czechia.CzechiaIdBackRecognizer;
import com.microblink.entities.recognizers.blinkid.czechia.CzechiaIdFrontRecognizer;
import com.microblink.entities.recognizers.blinkid.egypt.EgyptIdFrontRecognizer;
import com.microblink.entities.recognizers.blinkid.elitepaymentcard.ElitePaymentCardBackRecognizer;
import com.microblink.entities.recognizers.blinkid.elitepaymentcard.ElitePaymentCardCombinedRecognizer;
import com.microblink.entities.recognizers.blinkid.elitepaymentcard.ElitePaymentCardFrontRecognizer;
import com.microblink.entities.recognizers.blinkid.germany.GermanyCombinedRecognizer;
import com.microblink.entities.recognizers.blinkid.germany.GermanyIdBackRecognizer;
import com.microblink.entities.recognizers.blinkid.germany.GermanyIdFrontRecognizer;
import com.microblink.entities.recognizers.blinkid.germany.GermanyIdOldRecognizer;
import com.microblink.entities.recognizers.blinkid.hongkong.HongKongIdFrontRecognizer;
import com.microblink.entities.recognizers.blinkid.indonesia.IndonesiaIdFrontRecognizer;
import com.microblink.entities.recognizers.blinkid.jordan.JordanCombinedRecognizer;
import com.microblink.entities.recognizers.blinkid.jordan.JordanIdBackRecognizer;
import com.microblink.entities.recognizers.blinkid.jordan.JordanIdFrontRecognizer;
import com.microblink.entities.recognizers.blinkid.kuwait.KuwaitIdBackRecognizer;
import com.microblink.entities.recognizers.blinkid.kuwait.KuwaitIdFrontRecognizer;
import com.microblink.entities.recognizers.blinkid.malaysia.MalaysiaIkadFrontRecognizer;
import com.microblink.entities.recognizers.blinkid.malaysia.MalaysiaMyKadBackRecognizer;
import com.microblink.entities.recognizers.blinkid.malaysia.MalaysiaMyKadFrontRecognizer;
import com.microblink.entities.recognizers.blinkid.malaysia.MalaysiaMyTenteraFrontRecognizer;
import com.microblink.entities.recognizers.blinkid.morocco.MoroccoIdBackRecognizer;
import com.microblink.entities.recognizers.blinkid.morocco.MoroccoIdFrontRecognizer;
import com.microblink.entities.recognizers.blinkid.mrtd.MrtdCombinedRecognizer;
import com.microblink.entities.recognizers.blinkid.mrtd.MrtdRecognizer;
import com.microblink.entities.recognizers.blinkid.paymentcard.PaymentCardBackRecognizer;
import com.microblink.entities.recognizers.blinkid.paymentcard.PaymentCardCombinedRecognizer;
import com.microblink.entities.recognizers.blinkid.paymentcard.PaymentCardFrontRecognizer;
import com.microblink.entities.recognizers.blinkid.poland.PolandCombinedRecognizer;
import com.microblink.entities.recognizers.blinkid.poland.PolandIdBackRecognizer;
import com.microblink.entities.recognizers.blinkid.poland.PolandIdFrontRecognizer;
import com.microblink.entities.recognizers.blinkid.romania.RomaniaIdFrontRecognizer;
import com.microblink.entities.recognizers.blinkid.serbia.SerbiaCombinedRecognizer;
import com.microblink.entities.recognizers.blinkid.serbia.SerbiaIdBackRecognizer;
import com.microblink.entities.recognizers.blinkid.serbia.SerbiaIdFrontRecognizer;
import com.microblink.entities.recognizers.blinkid.singapore.SingaporeCombinedRecognizer;
import com.microblink.entities.recognizers.blinkid.singapore.SingaporeIdBackRecognizer;
import com.microblink.entities.recognizers.blinkid.singapore.SingaporeIdFrontRecognizer;
import com.microblink.entities.recognizers.blinkid.slovakia.SlovakiaCombinedRecognizer;
import com.microblink.entities.recognizers.blinkid.slovakia.SlovakiaIdBackRecognizer;
import com.microblink.entities.recognizers.blinkid.slovakia.SlovakiaIdFrontRecognizer;
import com.microblink.entities.recognizers.blinkid.slovenia.SloveniaCombinedRecognizer;
import com.microblink.entities.recognizers.blinkid.slovenia.SloveniaIdBackRecognizer;
import com.microblink.entities.recognizers.blinkid.slovenia.SloveniaIdFrontRecognizer;
import com.microblink.entities.recognizers.blinkid.switzerland.SwitzerlandIdBackRecognizer;
import com.microblink.entities.recognizers.blinkid.switzerland.SwitzerlandIdFrontRecognizer;
import com.microblink.entities.recognizers.blinkid.unitedArabEmirates.UnitedArabEmiratesIdBackRecognizer;
import com.microblink.entities.recognizers.blinkid.unitedArabEmirates.UnitedArabEmiratesIdFrontRecognizer;

import de.telekom.smartcredentials.documentscanner.utils.ScannerUtils;

public enum ScannerRecognizer {

    AUSTRIA_ID_CARD_COMBINED() {
        @NonNull
        @Override
        public Recognizer createRecognizer() {
            return new AustriaCombinedRecognizer();
        }
    },
    AUSTRIA_ID_CARD_FRONT_SIDE() {
        @NonNull
        @Override
        public Recognizer createRecognizer() {
            return new AustriaIdFrontRecognizer();
        }
    },
    AUSTRIA_ID_CARD_BACK_SIDE() {
        @NonNull
        @Override
        public Recognizer createRecognizer() {
            return new AustriaIdBackRecognizer();
        }
    },
    BRUNEI_ID_CARD_FRONT_SIDE() {
        @NonNull
        @Override
        protected Recognizer createRecognizer() {
            return new BruneiIdFrontRecognizer();
        }
    },
    BRUNEI_ID_CARD_BACK_SIDE() {
        @NonNull
        @Override
        protected Recognizer createRecognizer() {
            return new BruneiIdBackRecognizer();
        }
    },
    CYPRUS_ID_CARD_FRONT_SIDE() {
        @NonNull
        @Override
        protected Recognizer createRecognizer() {
            return new CyprusIdFrontRecognizer();
        }
    },
    CYPRUS_ID_CARD_BACK_SIDE() {
        @NonNull
        @Override
        protected Recognizer createRecognizer() {
            return new CyprusIdBackRecognizer();
        }
    },
    COLOMBIA_ID_CARD_BACK_SIDE() {
        @NonNull
        @Override
        public Recognizer createRecognizer() {
            return new ColombiaIdBackRecognizer();
        }
    },
    COLOMBIA_ID_CARD_FRONT_SIDE() {
        @NonNull
        @Override
        public Recognizer createRecognizer() {
            return new ColombiaIdFrontRecognizer();
        }
    },
    CROATIA_ID_CARD_FRONT_SIDE() {
        @NonNull
        @Override
        public Recognizer createRecognizer() {
            return new CroatiaIdFrontRecognizer();
        }
    },
    CROATIA_ID_CARD_BACK_SIDE() {
        @NonNull
        @Override
        public Recognizer createRecognizer() {
            return new CroatiaIdBackRecognizer();
        }
    },
    CROATIA_ID_CARD_COMBINED() {
        @NonNull
        @Override
        public Recognizer createRecognizer() {
            return new CroatiaCombinedRecognizer();
        }
    },
    CZECH_ID_CARD_FRONT_SIDE() {
        @NonNull
        @Override
        public Recognizer createRecognizer() {
            return new CzechiaIdFrontRecognizer();
        }
    },
    CZECH_ID_CARD_BACK_SIDE() {
        @NonNull
        @Override
        public Recognizer createRecognizer() {
            return new CzechiaIdBackRecognizer();
        }
    },
    CZECH_ID_CARD_COMBINED() {
        @NonNull
        @Override
        public Recognizer createRecognizer() {
            return new CzechiaCombinedRecognizer();
        }
    },
    EGYPT_ID_CARD_FRONT_SIDE() {
        @NonNull
        @Override
        public Recognizer createRecognizer() {
            return new EgyptIdFrontRecognizer();
        }
    },
    GERMANY_ID_CARD_FRONT_SIDE() {
        @NonNull
        @Override
        public Recognizer createRecognizer() {
            return new GermanyIdFrontRecognizer();
        }
    },
    GERMANY_ID_CARD_BACK_SIDE() {
        @NonNull
        @Override
        public Recognizer createRecognizer() {
            return new GermanyIdBackRecognizer();
        }
    },
    GERMANY_ID_CARD_COMBINED() {
        @NonNull
        @Override
        public Recognizer createRecognizer() {
            return new GermanyCombinedRecognizer();
        }
    },
    GERMANY_ID_CARD_OLD() {
        @NonNull
        @Override
        public Recognizer createRecognizer() {
            return new GermanyIdOldRecognizer();
        }
    },
    HONG_KONG_ID_CARD_FRONT_SIDE() {
        @NonNull
        @Override
        public Recognizer createRecognizer() {
            return new HongKongIdFrontRecognizer();
        }
    },
    MALAYSIA_IKAD_FRONT_SIDE() {
        @NonNull
        @Override
        public Recognizer createRecognizer() {
            return new MalaysiaIkadFrontRecognizer();
        }
    },
    INDONESIA_ID_CARD_FRONT_SIDE() {
        @NonNull
        @Override
        public Recognizer createRecognizer() {
            return new IndonesiaIdFrontRecognizer();
        }
    },
    JORDAN_ID_CARD_FRONT_SIDE() {
        @NonNull
        @Override
        public Recognizer createRecognizer() {
            return new JordanIdFrontRecognizer();
        }
    },
    JORDAN_ID_CARD_BACK_SIDE() {
        @NonNull
        @Override
        public Recognizer createRecognizer() {
            return new JordanIdBackRecognizer();
        }
    },
    JORDAN_ID_CARD_COMBINED() {
        @NonNull
        @Override
        public Recognizer createRecognizer() {
            return new JordanCombinedRecognizer();
        }
    },
    KUWAIT_ID_CARD_FRONT_SIDE() {
        @NonNull
        @Override
        protected Recognizer createRecognizer() {
            return new KuwaitIdFrontRecognizer();
        }
    },
    KUWAIT_ID_CARD_BACK_SIDE() {
        @NonNull
        @Override
        protected Recognizer createRecognizer() {
            return new KuwaitIdBackRecognizer();
        }
    },
    MACHINE_READABLE_ZONE() {
        @NonNull
        @Override
        public Recognizer createRecognizer() {
            return new MrtdRecognizer();
        }
    },
    MACHINE_READABLE_ZONE_COMBINED() {
        @NonNull
        @Override
        public Recognizer createRecognizer() {
            return new MrtdCombinedRecognizer();
        }
    },
    MALAYSIA_MY_KAD_FRONT_SIDE() {
        @NonNull
        @Override
        public Recognizer createRecognizer() {
            return new MalaysiaMyKadFrontRecognizer();
        }
    },
    MALAYSIA_MY_KAD_BACK_SIDE() {
        @NonNull
        @Override
        public Recognizer createRecognizer() {
            return new MalaysiaMyKadBackRecognizer();
        }
    },
    MALAYSIA_MY_TENTERA_FRONT_SIDE() {
        @NonNull
        @Override
        public Recognizer createRecognizer() {
            return new MalaysiaMyTenteraFrontRecognizer();
        }
    },
    MOROCCO_ID_CARD_FRONT_SIDE() {
        @NonNull
        @Override
        protected Recognizer createRecognizer() {
            return new MoroccoIdFrontRecognizer();
        }
    },
    MOROCCO_ID_CARD_BACK_SIDE() {
        @NonNull
        @Override
        protected Recognizer createRecognizer() {
            return new MoroccoIdBackRecognizer();
        }
    },
    POLAND_ID_CARD_FRONT_SIDE() {
        @NonNull
        @Override
        public Recognizer createRecognizer() {
            return new PolandIdFrontRecognizer();
        }
    },
    POLAND_ID_CARD_BACK_SIDE() {
        @NonNull
        @Override
        public Recognizer createRecognizer() {
            return new PolandIdBackRecognizer();
        }
    },
    POLAND_ID_CARD_COMBINED() {
        @NonNull
        @Override
        public Recognizer createRecognizer() {
            return new PolandCombinedRecognizer();
        }
    },
    ROMANIA_ID_CARD_FRONT_SIDE() {
        @NonNull
        @Override
        public Recognizer createRecognizer() {
            return new RomaniaIdFrontRecognizer();
        }
    },
    @Deprecated
    SERBIA_ID_CARD_FRONT_SIDE() {
        @NonNull
        @Override
        public Recognizer createRecognizer() {
            return new SerbiaIdFrontRecognizer();
        }
    },
    @Deprecated
    SERBIA_ID_CARD_BACK_SIDE() {
        @NonNull
        @Override
        public Recognizer createRecognizer() {
            return new SerbiaIdBackRecognizer();
        }
    },
    @Deprecated
    SERBIA_ID_CARD_COMBINED() {
        @NonNull
        @Override
        public Recognizer createRecognizer() {
            return new SerbiaCombinedRecognizer();
        }
    },
    SINGAPORE_ID_CARD_FRONT_SIDE() {
        @NonNull
        @Override
        public Recognizer createRecognizer() {
            return new SingaporeIdFrontRecognizer();
        }
    },
    SINGAPORE_ID_CARD_BACK_SIDE() {
        @NonNull
        @Override
        public Recognizer createRecognizer() {
            return new SingaporeIdBackRecognizer();
        }
    },
    SINGAPORE_ID_CARD_COMBINED() {
        @NonNull
        @Override
        public Recognizer createRecognizer() {
            return new SingaporeCombinedRecognizer();
        }
    },
    SLOVAKIA_ID_CARD_FRONT_SIDE() {
        @NonNull
        @Override
        public Recognizer createRecognizer() {
            return new SlovakiaIdFrontRecognizer();
        }
    },
    SLOVAKIA_ID_CARD_BACK_SIDE() {
        @NonNull
        @Override
        public Recognizer createRecognizer() {
            return new SlovakiaIdBackRecognizer();
        }
    },
    SLOVAKIA_ID_CARD_COMBINED() {
        @NonNull
        @Override
        public Recognizer createRecognizer() {
            return new SlovakiaCombinedRecognizer();
        }
    },
    SLOVENIA_ID_CARD_FRONT_SIDE() {
        @NonNull
        @Override
        public Recognizer createRecognizer() {
            return new SloveniaIdFrontRecognizer();
        }
    },
    SLOVENIA_ID_CARD_BACK_SIDE() {
        @NonNull
        @Override
        public Recognizer createRecognizer() {
            return new SloveniaIdBackRecognizer();
        }
    },
    SLOVENIA_ID_CARD_COMBINED() {
        @NonNull
        @Override
        public Recognizer createRecognizer() {
            return new SloveniaCombinedRecognizer();
        }
    },
    SWITZERLAND_ID_CARD_FRONT_SIDE() {
        @NonNull
        @Override
        public Recognizer createRecognizer() {
            return new SwitzerlandIdFrontRecognizer();
        }
    },
    SWITZERLAND_ID_CARD_BACK_SIDE() {
        @NonNull
        @Override
        public Recognizer createRecognizer() {
            return new SwitzerlandIdBackRecognizer();
        }
    },
    UAE_ID_CARD_FRONT_SIDE() {
        @NonNull
        @Override
        public Recognizer createRecognizer() {
            return new UnitedArabEmiratesIdFrontRecognizer();
        }
    },
    UAE_ID_CARD_BACK_SIDE() {
        @NonNull
        @Override
        public Recognizer createRecognizer() {
            return new UnitedArabEmiratesIdBackRecognizer();
        }
    },
    PAYMENT_CARD_FRONT_SIDE() {
        @NonNull
        @Override
        protected Recognizer createRecognizer() {
            PaymentCardFrontRecognizer frontSideRecognizer = new PaymentCardFrontRecognizer();
            frontSideRecognizer.setExtractOwner(true);
            return frontSideRecognizer;
        }
    },
    PAYMENT_CARD_BACK_SIDE() {
        @NonNull
        @Override
        protected Recognizer createRecognizer() {
            return new PaymentCardBackRecognizer();
        }
    },
    PAYMENT_CARD_COMBINED() {
        @NonNull
        @Override
        protected Recognizer createRecognizer() {
            PaymentCardCombinedRecognizer combinedRecognizer = new PaymentCardCombinedRecognizer();
            combinedRecognizer.setExtractOwner(true);
            return combinedRecognizer;
        }
    },
    ELITE_PAYMENT_CARD_FRONT_SIDE() {
        @NonNull
        @Override
        protected Recognizer createRecognizer() {
            ElitePaymentCardFrontRecognizer eliteFrontSideRecognizer = new ElitePaymentCardFrontRecognizer();
            eliteFrontSideRecognizer.setExtractOwner(true);
            return eliteFrontSideRecognizer;
        }
    },
    ELITE_PAYMENT_CARD_BACK_SIDE() {
        @NonNull
        @Override
        protected Recognizer createRecognizer() {
            return new ElitePaymentCardBackRecognizer();
        }
    },
    ELITE_PAYMENT_CARD_COMBINED() {
        @NonNull
        @Override
        protected Recognizer createRecognizer() {
            ElitePaymentCardCombinedRecognizer eliteCombinedRecognizer = new ElitePaymentCardCombinedRecognizer();
            eliteCombinedRecognizer.setExtractOwner(true);
            return eliteCombinedRecognizer;
        }
    };

    @NonNull
    protected abstract Recognizer createRecognizer();

    public Recognizer getRecognizer() {
        Recognizer recognizer = createRecognizer();
        ScannerUtils.enableImages(recognizer);
        return recognizer;
    }
}
