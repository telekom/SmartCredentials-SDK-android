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

package de.telekom.smartcredentials.documentscanner.utils;

import androidx.annotation.NonNull;

import com.microblink.entities.Entity;
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
import com.microblink.entities.recognizers.blinkid.mrtd.MrzResult;
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

import de.telekom.smartcredentials.documentscanner.model.austria.AustriaBackSideResult;
import de.telekom.smartcredentials.documentscanner.model.austria.AustriaCombinedResult;
import de.telekom.smartcredentials.documentscanner.model.austria.AustriaFrontSideResult;
import de.telekom.smartcredentials.documentscanner.model.brunei.BruneiBackSideResult;
import de.telekom.smartcredentials.documentscanner.model.brunei.BruneiFrontSideResult;
import de.telekom.smartcredentials.documentscanner.model.colombia.ColombiaBackSideResult;
import de.telekom.smartcredentials.documentscanner.model.colombia.ColombiaFrontSideResult;
import de.telekom.smartcredentials.documentscanner.model.croatia.CroatiaBackSideResult;
import de.telekom.smartcredentials.documentscanner.model.croatia.CroatiaCombinedResult;
import de.telekom.smartcredentials.documentscanner.model.croatia.CroatiaFrontSideResult;
import de.telekom.smartcredentials.documentscanner.model.cyprus.CyprusBackSideResult;
import de.telekom.smartcredentials.documentscanner.model.cyprus.CyprusFrontSideResult;
import de.telekom.smartcredentials.documentscanner.model.czech.CzechBackSideResult;
import de.telekom.smartcredentials.documentscanner.model.czech.CzechCombinedResult;
import de.telekom.smartcredentials.documentscanner.model.czech.CzechFrontSideResult;
import de.telekom.smartcredentials.documentscanner.model.egypt.EgyptFrontSideResult;
import de.telekom.smartcredentials.documentscanner.model.germany.GermanyBackSideResult;
import de.telekom.smartcredentials.documentscanner.model.germany.GermanyCombinedResult;
import de.telekom.smartcredentials.documentscanner.model.germany.GermanyFrontSideResult;
import de.telekom.smartcredentials.documentscanner.model.germany.GermanyOldResult;
import de.telekom.smartcredentials.documentscanner.model.hongkong.HongKongFrontSideResult;
import de.telekom.smartcredentials.documentscanner.model.ikad.IkadFrontSideResult;
import de.telekom.smartcredentials.documentscanner.model.indonesia.IndonesiaFrontSideResult;
import de.telekom.smartcredentials.documentscanner.model.jordan.JordanBackSideResult;
import de.telekom.smartcredentials.documentscanner.model.jordan.JordanCombinedResult;
import de.telekom.smartcredentials.documentscanner.model.jordan.JordanFrontSideResult;
import de.telekom.smartcredentials.documentscanner.model.kuwait.KuwaitBackSideResult;
import de.telekom.smartcredentials.documentscanner.model.kuwait.KuwaitFrontSideResult;
import de.telekom.smartcredentials.documentscanner.model.morocco.MoroccoBackSideResult;
import de.telekom.smartcredentials.documentscanner.model.morocco.MoroccoFrontSideResult;
import de.telekom.smartcredentials.documentscanner.model.mrz.MRZCombinedResult;
import de.telekom.smartcredentials.documentscanner.model.mrz.MRZResult;
import de.telekom.smartcredentials.documentscanner.model.mykad.MyKadBackSideResult;
import de.telekom.smartcredentials.documentscanner.model.mykad.MyKadFrontSideResult;
import de.telekom.smartcredentials.documentscanner.model.mytentera.MalaysiaMyTenteraFrontSideResult;
import de.telekom.smartcredentials.documentscanner.model.payment.ElitePaymentCardBackSideResult;
import de.telekom.smartcredentials.documentscanner.model.payment.ElitePaymentCardCombinedResult;
import de.telekom.smartcredentials.documentscanner.model.payment.ElitePaymentCardFrontSideResult;
import de.telekom.smartcredentials.documentscanner.model.payment.PaymentCardBackSideResult;
import de.telekom.smartcredentials.documentscanner.model.payment.PaymentCardCombinedResult;
import de.telekom.smartcredentials.documentscanner.model.payment.PaymentCardFrontSideResult;
import de.telekom.smartcredentials.documentscanner.model.poland.PolandBackSideResult;
import de.telekom.smartcredentials.documentscanner.model.poland.PolandCombinedResult;
import de.telekom.smartcredentials.documentscanner.model.poland.PolandFrontSideResult;
import de.telekom.smartcredentials.documentscanner.model.romania.RomaniaFrontSideResult;
import de.telekom.smartcredentials.documentscanner.model.serbia.SerbiaBackSideResult;
import de.telekom.smartcredentials.documentscanner.model.serbia.SerbiaCombinedResult;
import de.telekom.smartcredentials.documentscanner.model.serbia.SerbiaFrontSideResult;
import de.telekom.smartcredentials.documentscanner.model.singapore.SingaporeBackSideResult;
import de.telekom.smartcredentials.documentscanner.model.singapore.SingaporeCombinedResult;
import de.telekom.smartcredentials.documentscanner.model.singapore.SingaporeFrontSideResult;
import de.telekom.smartcredentials.documentscanner.model.slovakia.SlovakiaBackSideResult;
import de.telekom.smartcredentials.documentscanner.model.slovakia.SlovakiaCombinedResult;
import de.telekom.smartcredentials.documentscanner.model.slovakia.SlovakiaFrontSideResult;
import de.telekom.smartcredentials.documentscanner.model.slovenia.SloveniaBackSideResult;
import de.telekom.smartcredentials.documentscanner.model.slovenia.SloveniaCombinedResult;
import de.telekom.smartcredentials.documentscanner.model.slovenia.SloveniaFrontSideResult;
import de.telekom.smartcredentials.documentscanner.model.swiss.SwissBackSideResult;
import de.telekom.smartcredentials.documentscanner.model.swiss.SwissFrontSideResult;
import de.telekom.smartcredentials.documentscanner.model.uae.UAEBackSideResult;
import de.telekom.smartcredentials.documentscanner.model.uae.UAEFrontSideResult;
import de.telekom.smartcredentials.core.model.DocumentScannerResult;


public class ScannerResultConverter {

    private ScannerResultConverter() {
        // required empty constructor
    }

    public static DocumentScannerResult convertToInternalModel(Entity.Result result) {
        DocumentScannerResult documentScannerResult = null;
        if (result instanceof AustriaIdFrontRecognizer.Result) {
            documentScannerResult = parseAustriaFrontSide((AustriaIdFrontRecognizer.Result) result);
        } else if (result instanceof AustriaIdBackRecognizer.Result) {
            documentScannerResult = parseAustriaBackSide((AustriaIdBackRecognizer.Result) result);
        } else if (result instanceof AustriaCombinedRecognizer.Result) {
            documentScannerResult = parseAustriaCombined((AustriaCombinedRecognizer.Result) result);
        } else if (result instanceof CyprusIdFrontRecognizer.Result) {
            documentScannerResult = parseCyprusFrontSide((CyprusIdFrontRecognizer.Result) result);
        } else if (result instanceof CyprusIdBackRecognizer.Result) {
            documentScannerResult = parseCyprusBackSide((CyprusIdBackRecognizer.Result) result);
        } else if (result instanceof ColombiaIdBackRecognizer.Result) {
            documentScannerResult = parseColombiaBackSide((ColombiaIdBackRecognizer.Result) result);
        } else if (result instanceof ColombiaIdFrontRecognizer.Result) {
            documentScannerResult = parseColombiaFrontSide((ColombiaIdFrontRecognizer.Result) result);
        } else if (result instanceof CroatiaIdFrontRecognizer.Result) {
            documentScannerResult = parseCroatiaFrontSide((CroatiaIdFrontRecognizer.Result) result);
        } else if (result instanceof CroatiaIdBackRecognizer.Result) {
            documentScannerResult = parseCroatiaBackSide((CroatiaIdBackRecognizer.Result) result);
        } else if (result instanceof CroatiaCombinedRecognizer.Result) {
            documentScannerResult = parseCroatiaCombined((CroatiaCombinedRecognizer.Result) result);
        } else if (result instanceof CzechiaIdFrontRecognizer.Result) {
            documentScannerResult = parseCzechFrontSide((CzechiaIdFrontRecognizer.Result) result);
        } else if (result instanceof CzechiaIdBackRecognizer.Result) {
            documentScannerResult = parseCzechBackSide((CzechiaIdBackRecognizer.Result) result);
        } else if (result instanceof CzechiaCombinedRecognizer.Result) {
            documentScannerResult = parseCzechCombined((CzechiaCombinedRecognizer.Result) result);
        } else if (result instanceof EgyptIdFrontRecognizer.Result) {
            documentScannerResult = parseEgyptFrontSide((EgyptIdFrontRecognizer.Result) result);
        } else if (result instanceof GermanyIdFrontRecognizer.Result) {
            documentScannerResult = parseGermanyFrontSide((GermanyIdFrontRecognizer.Result) result);
        } else if (result instanceof GermanyIdBackRecognizer.Result) {
            documentScannerResult = parseGermanyBackSide((GermanyIdBackRecognizer.Result) result);
        } else if (result instanceof GermanyCombinedRecognizer.Result) {
            documentScannerResult = parseGermanyCombined((GermanyCombinedRecognizer.Result) result);
        } else if (result instanceof GermanyIdOldRecognizer.Result) {
            documentScannerResult = parseGermanyOld((GermanyIdOldRecognizer.Result) result);
        } else if (result instanceof HongKongIdFrontRecognizer.Result) {
            documentScannerResult = parseHongKongFrontSide((HongKongIdFrontRecognizer.Result) result);
        } else if (result instanceof MalaysiaIkadFrontRecognizer.Result) {
            documentScannerResult = parseIkad((MalaysiaIkadFrontRecognizer.Result) result);
        } else if (result instanceof IndonesiaIdFrontRecognizer.Result) {
            documentScannerResult = parseIndonesiaFrontSide((IndonesiaIdFrontRecognizer.Result) result);
        } else if (result instanceof JordanIdFrontRecognizer.Result) {
            documentScannerResult = parseJordanFrontSide((JordanIdFrontRecognizer.Result) result);
        } else if (result instanceof JordanIdBackRecognizer.Result) {
            documentScannerResult = parseJordanBackSide((JordanIdBackRecognizer.Result) result);
        } else if (result instanceof JordanCombinedRecognizer.Result) {
            documentScannerResult = parseJordanCombined((JordanCombinedRecognizer.Result) result);
        } else if (result instanceof KuwaitIdFrontRecognizer.Result) {
            documentScannerResult = parseKuwaitFrontSide((KuwaitIdFrontRecognizer.Result) result);
        } else if (result instanceof KuwaitIdBackRecognizer.Result) {
            documentScannerResult = parseKuwaitBackSide((KuwaitIdBackRecognizer.Result) result);
        } else if (result instanceof MrtdRecognizer.Result) {
            documentScannerResult = parseMrtd((MrtdRecognizer.Result) result);
        } else if (result instanceof MrtdCombinedRecognizer.Result) {
            documentScannerResult = parseMrtdCombined((MrtdCombinedRecognizer.Result) result);
        } else if (result instanceof MalaysiaMyKadFrontRecognizer.Result) {
            documentScannerResult = parseMyKadFrontSide((MalaysiaMyKadFrontRecognizer.Result) result);
        } else if (result instanceof MalaysiaMyKadBackRecognizer.Result) {
            documentScannerResult = parseMyKadBackSide((MalaysiaMyKadBackRecognizer.Result) result);
        } else if (result instanceof MalaysiaMyTenteraFrontRecognizer.Result) {
            documentScannerResult = parseMalaysiaFrontSide((MalaysiaMyTenteraFrontRecognizer.Result) result);
        } else if (result instanceof MoroccoIdFrontRecognizer.Result) {
            documentScannerResult = parseMoroccoFrontSide((MoroccoIdFrontRecognizer.Result) result);
        } else if (result instanceof MoroccoIdBackRecognizer.Result) {
            documentScannerResult = parseMoroccoBackSide((MoroccoIdBackRecognizer.Result) result);
        } else if (result instanceof PolandIdFrontRecognizer.Result) {
            documentScannerResult = parsePolandFrontSide((PolandIdFrontRecognizer.Result) result);
        } else if (result instanceof PolandIdBackRecognizer.Result) {
            documentScannerResult = parsePolandBackSide((PolandIdBackRecognizer.Result) result);
        } else if (result instanceof PolandCombinedRecognizer.Result) {
            documentScannerResult = parsePolandCombined((PolandCombinedRecognizer.Result) result);
        } else if (result instanceof RomaniaIdFrontRecognizer.Result) {
            documentScannerResult = parseRomaniaFrontSide((RomaniaIdFrontRecognizer.Result) result);
        } else if (result instanceof SerbiaIdFrontRecognizer.Result) {
            documentScannerResult = parseSerbiaFrontSide((SerbiaIdFrontRecognizer.Result) result);
        } else if (result instanceof SerbiaIdBackRecognizer.Result) {
            documentScannerResult = parseSerbiaBackSide((SerbiaIdBackRecognizer.Result) result);
        } else if (result instanceof SerbiaCombinedRecognizer.Result) {
            documentScannerResult = parseSerbiaCombined((SerbiaCombinedRecognizer.Result) result);
        } else if (result instanceof SingaporeIdFrontRecognizer.Result) {
            documentScannerResult = parseSingaporeFrontSide((SingaporeIdFrontRecognizer.Result) result);
        } else if (result instanceof SingaporeIdBackRecognizer.Result) {
            documentScannerResult = parseSingaporeBackSide((SingaporeIdBackRecognizer.Result) result);
        } else if (result instanceof SingaporeCombinedRecognizer.Result) {
            documentScannerResult = parseSingaporeCombined((SingaporeCombinedRecognizer.Result) result);
        } else if (result instanceof SlovakiaIdFrontRecognizer.Result) {
            documentScannerResult = parseSlovakiaFrontSide((SlovakiaIdFrontRecognizer.Result) result);
        } else if (result instanceof SlovakiaIdBackRecognizer.Result) {
            documentScannerResult = parseSlovakiaBackSide((SlovakiaIdBackRecognizer.Result) result);
        } else if (result instanceof SlovakiaCombinedRecognizer.Result) {
            documentScannerResult = parseSlovakiaCombined((SlovakiaCombinedRecognizer.Result) result);
        } else if (result instanceof SloveniaIdFrontRecognizer.Result) {
            documentScannerResult = parseSloveniaFrontSide((SloveniaIdFrontRecognizer.Result) result);
        } else if (result instanceof SloveniaIdBackRecognizer.Result) {
            documentScannerResult = parseSloveniaBackSide((SloveniaIdBackRecognizer.Result) result);
        } else if (result instanceof SloveniaCombinedRecognizer.Result) {
            documentScannerResult = parseSloveniaCombined((SloveniaCombinedRecognizer.Result) result);
        } else if (result instanceof SwitzerlandIdFrontRecognizer.Result) {
            documentScannerResult = parseSwissFrontSide((SwitzerlandIdFrontRecognizer.Result) result);
        } else if (result instanceof SwitzerlandIdBackRecognizer.Result) {
            documentScannerResult = parseSwissBackSide((SwitzerlandIdBackRecognizer.Result) result);
        } else if (result instanceof UnitedArabEmiratesIdFrontRecognizer.Result) {
            documentScannerResult = parseUAEFrontSide((UnitedArabEmiratesIdFrontRecognizer.Result) result);
        } else if (result instanceof UnitedArabEmiratesIdBackRecognizer.Result) {
            documentScannerResult = parseUAEBackSide((UnitedArabEmiratesIdBackRecognizer.Result) result);
        } else if (result instanceof PaymentCardFrontRecognizer.Result) {
            documentScannerResult = parsePaymentCardFrontSide((PaymentCardFrontRecognizer.Result) result);
        } else if (result instanceof PaymentCardBackRecognizer.Result) {
            documentScannerResult = parsePaymentCardBackSide((PaymentCardBackRecognizer.Result) result);
        } else if (result instanceof PaymentCardCombinedRecognizer.Result) {
            documentScannerResult = parsePaymentCardCombined((PaymentCardCombinedRecognizer.Result) result);
        } else if (result instanceof ElitePaymentCardFrontRecognizer.Result) {
            documentScannerResult = parseElitePaymentCardFront((ElitePaymentCardFrontRecognizer.Result) result);
        } else if (result instanceof ElitePaymentCardBackRecognizer.Result) {
            documentScannerResult = parseElitePaymentCardBack((ElitePaymentCardBackRecognizer.Result) result);
        } else if (result instanceof ElitePaymentCardCombinedRecognizer.Result) {
            documentScannerResult = parseElitePaymentCombined((ElitePaymentCardCombinedRecognizer.Result) result);
        } else if (result instanceof BruneiIdFrontRecognizer.Result) {
            documentScannerResult = parseBruneiFrontSide((BruneiIdFrontRecognizer.Result) result);
        } else if (result instanceof BruneiIdBackRecognizer.Result) {
            documentScannerResult = parseBruneiBackSide((BruneiIdBackRecognizer.Result) result);
        }

        return documentScannerResult;
    }

    private static DocumentScannerResult parseBruneiBackSide(BruneiIdBackRecognizer.Result result) {
        MrzResult mrzResult = result.getMrzResult();

        return new BruneiBackSideResult(result.getResultState())
                .setAddress(result.getAddress())
                .setDateOfIssue(ModelConverter.convertDate(result.getDateOfIssue()))
                .setRace(result.getRace())
                .setAlienNr(mrzResult.getAlienNumber())
                .setApplicationReceiptNumber(mrzResult.getApplicationReceiptNumber())
                .setDateOfBirth(ModelConverter.convertDate(mrzResult.getDateOfBirth()))
                .setDateOfExpiry(ModelConverter.convertDate(mrzResult.getDateOfExpiry()))
                .setDocumentCode(mrzResult.getDocumentCode())
                .setDocumentNumber(mrzResult.getDocumentNumber())
                .setSex(mrzResult.getGender())
                .setImmigrantCaseNumber(mrzResult.getImmigrantCaseNumber())
                .setIssuer(mrzResult.getIssuer())
                .setMrzText(mrzResult.getMrzText())
                .setNationality(mrzResult.getNationality())
                .setOpt1(mrzResult.getOpt1())
                .setOpt2(mrzResult.getOpt2())
                .setPrimaryId(mrzResult.getPrimaryId())
                .setSecondaryId(mrzResult.getSecondaryId())
                .setMrzVerified(mrzResult.isMrzVerified());
    }

    private static DocumentScannerResult parseBruneiFrontSide(BruneiIdFrontRecognizer.Result result) {
        return new BruneiFrontSideResult(result.getResultState())
                .setDateOfBirth(ModelConverter.convertDate(result.getDateOfBirth()))
                .setDocumentNumber(result.getDocumentNumber())
                .setFaceImage(ModelConverter.convertImageToBitmap(result.getFaceImage()))
                .setFullName(result.getFullName())
                .setPlaceOfBirth(result.getPlaceOfBirth())
                .setSex(result.getSex());
    }

    private static DocumentScannerResult parseElitePaymentCombined(ElitePaymentCardCombinedRecognizer.Result result) {
        return new ElitePaymentCardCombinedResult(result.getResultState())
                .setCardNumber(result.getCardNumber())
                .setCvv(result.getCvv())
                .setInventoryNumber(result.getInventoryNumber())
                .setOwner(result.getOwner())
                .setValidThru(ModelConverter.convertDate(result.getValidThru()))
                .setDigitalSignature(result.getDigitalSignature())
                .setDigitalSignatureVersion(result.getDigitalSignatureVersion())
                .setEncodedBackFullDocumentImage(result.getEncodedBackFullDocumentImage())
                .setEncodedFrontFullDocumentImage(result.getEncodedFrontFullDocumentImage())
                .setDocumentDataMatch(result.isDocumentDataMatch())
                .setScanningFirstSideDone(result.isScanningFirstSideDone());
    }

    private static DocumentScannerResult parseElitePaymentCardBack(ElitePaymentCardBackRecognizer.Result result) {
        return new ElitePaymentCardBackSideResult(result.getResultState())
                .setCardNumber(result.getCardNumber())
                .setCvv(result.getCvv())
                .setDocumentImage(ModelConverter.convertImageToBitmap(result.getFullDocumentImage()))
                .setInventoryNumber(result.getInventoryNumber())
                .setValidThru(ModelConverter.convertDate(result.getValidThru()));
    }

    private static DocumentScannerResult parseElitePaymentCardFront(ElitePaymentCardFrontRecognizer.Result result) {
        return new ElitePaymentCardFrontSideResult(result.getResultState())
                .setDocumentImage(ModelConverter.convertImageToBitmap(result.getFullDocumentImage()))
                .setOwner(result.getOwner());
    }

    private static DocumentScannerResult parsePaymentCardCombined(PaymentCardCombinedRecognizer.Result result) {
        return new PaymentCardCombinedResult(result.getResultState())
                .setCardNumber(result.getCardNumber())
                .setCvv(result.getCvv())
                .setInventoryNumber(result.getInventoryNumber())
                .setOwner(result.getOwner())
                .setValidThru(ModelConverter.convertDate(result.getValidThru()))
                .setDocumentDataMatch(result.isDocumentDataMatch())
                .setDigitalSignature(result.getDigitalSignature())
                .setDigitalSignatureVersion(result.getDigitalSignatureVersion())
                .setEncodedFrontFullDocumentImage(result.getEncodedFrontFullDocumentImage())
                .setEncodedBackFullDocumentImage(result.getEncodedBackFullDocumentImage())
                .setScanningFirstSideDone(result.isScanningFirstSideDone());
    }

    private static DocumentScannerResult parsePaymentCardBackSide(PaymentCardBackRecognizer.Result result) {
        return new PaymentCardBackSideResult(result.getResultState())
                .setCvv(result.getCvv())
                .setDocumentImage(ModelConverter.convertImageToBitmap(result.getFullDocumentImage()))
                .setInventoryNumber(result.getInventoryNumber());
    }

    private static DocumentScannerResult parsePaymentCardFrontSide(PaymentCardFrontRecognizer.Result result) {
        return new PaymentCardFrontSideResult(result.getResultState())
                .setCardNumber(result.getCardNumber())
                .setDocumentImage(ModelConverter.convertImageToBitmap(result.getFullDocumentImage()))
                .setOwner(result.getOwner())
                .setValidThru(ModelConverter.convertDate(result.getValidThru()));
    }

    private static DocumentScannerResult parseMoroccoBackSide(@NonNull MoroccoIdBackRecognizer.Result result) {
        return new MoroccoBackSideResult(result.getResultState())
                .setAddress(result.getAddress())
                .setCivilStatusNumber(result.getCivilStatusNumber())
                .setDateOfExpiry(ModelConverter.convertDate(result.getDateOfExpiry()))
                .setDocumentNumber(result.getDocumentNumber())
                .setFathersName(result.getFathersName())
                .setMothersName(result.getMothersName())
                .setSex(result.getSex());
    }

    private static DocumentScannerResult parseMoroccoFrontSide(@NonNull MoroccoIdFrontRecognizer.Result result) {
        return new MoroccoFrontSideResult(result.getResultState())
                .setDateOfBirth(ModelConverter.convertDate(result.getDateOfBirth()))
                .setDateOfExpiry(ModelConverter.convertDate(result.getDateOfExpiry()))
                .setDocumentNumber(result.getDocumentNumber())
                .setEncodedFaceImage(ModelConverter.encodeImage(result.getFaceImage()))
                .setName(result.getName())
                .setPlaceOfBirth(result.getPlaceOfBirth())
                .setSex(result.getSex())
                .setSurname(result.getSurname());
    }

    private static DocumentScannerResult parseKuwaitBackSide(@NonNull KuwaitIdBackRecognizer.Result result) {
        MrzResult mrzResult = result.getMrzResult();
        return new KuwaitBackSideResult(result.getResultState())
                .setSerialNumber(result.getSerialNo())
                .setAlienNr(mrzResult.getAlienNumber())
                .setApplicationReceiptNumber(mrzResult.getApplicationReceiptNumber())
                .setDateOfBirth(ModelConverter.convertDate(mrzResult.getDateOfBirth()))
                .setDateOfExpiry(ModelConverter.convertDate(mrzResult.getDateOfExpiry()))
                .setDocumentCode(mrzResult.getDocumentCode())
                .setDocumentNumber(mrzResult.getDocumentNumber())
                .setSex(mrzResult.getGender())
                .setImmigrantCaseNumber(mrzResult.getImmigrantCaseNumber())
                .setIssuer(mrzResult.getIssuer())
                .setMrzText(mrzResult.getMrzText())
                .setNationality(mrzResult.getNationality())
                .setOpt1(mrzResult.getOpt1())
                .setOpt2(mrzResult.getOpt2())
                .setPrimaryId(mrzResult.getPrimaryId())
                .setSecondaryId(mrzResult.getSecondaryId())
                .setMrzVerified(mrzResult.isMrzVerified());
    }

    private static DocumentScannerResult parseKuwaitFrontSide(@NonNull KuwaitIdFrontRecognizer.Result result) {
        return new KuwaitFrontSideResult(result.getResultState())
                .setDateOfBirth(ModelConverter.convertDate(result.getBirthDate()))
                .setCivilIdNumber(result.getCivilIdNumber())
                .setEncodedFaceImage(ModelConverter.encodeImage(result.getFaceImage()))
                .setDateOfExpiry(ModelConverter.convertDate(result.getExpiryDate()))
                .setName(result.getName())
                .setNationality(result.getNationality())
                .setSex(result.getSex());
    }

    private static DocumentScannerResult parseCyprusBackSide(@NonNull CyprusIdBackRecognizer.Result result) {
        return new CyprusBackSideResult(result.getResultState())
                .setDateOfBirth(ModelConverter.convertDate(result.getMrzResult().getDateOfBirth()))
                .setDateOfExpiry(ModelConverter.convertDate(result.getMrzResult().getDateOfExpiry()));
    }

    private static DocumentScannerResult parseCyprusFrontSide(@NonNull CyprusIdFrontRecognizer.Result result) {
        return new CyprusFrontSideResult(result.getResultState())
                .setEncodedFaceImage(ModelConverter.encodeImage(result.getFaceImage()))
                .setIdNumber(result.getIdNumber());
    }

    private static DocumentScannerResult parseAustriaFrontSide(@NonNull AustriaIdFrontRecognizer.Result result) {
        return new AustriaFrontSideResult(result.getResultState())
                .setDateOfBirth(ModelConverter.convertDate(result.getDateOfBirth()))
                .setDocumentNumber(result.getDocumentNumber())
                .setEncodedFaceImage(ModelConverter.encodeImage(result.getFaceImage()))
                .setEncodedFullDocumentImage(result.getEncodedFullDocumentImage())
                .setEncodedSignatureImage(result.getEncodedSignatureImage())
                .setLastName(result.getGivenName())
                .setSex(result.getSex())
                .setFirstName(result.getSurname());
    }

    private static DocumentScannerResult parseAustriaBackSide(@NonNull AustriaIdBackRecognizer.Result result) {
        return new AustriaBackSideResult(result.getResultState())
                .setDateOfIssue(ModelConverter.convertDate(result.getDateOfIssuance()))
                .setDocumentNumber(result.getDocumentNumber())
                .setEncodedFullDocumentImage(result.getEncodedFullDocumentImage())
                .setEyeColor(result.getEyeColour())
                .setHeight(result.getHeight())
                .setIssuingAuthority(result.getIssuingAuthority())
                .setMrzText(result.getMrzResult().getMrzText())
                .setPlaceOfBirth(result.getPlaceOfBirth())
                .setResidence(result.getPrincipalResidence());
    }

    private static DocumentScannerResult parseAustriaCombined(@NonNull AustriaCombinedRecognizer.Result result) {
        return new AustriaCombinedResult(result.getResultState())
                .setDateOfBirth(ModelConverter.convertDate(result.getDateOfBirth()))
                .setDateOfExpiry(ModelConverter.convertDate(result.getDateOfExpiry()))
                .setDateOfIssue(ModelConverter.convertDate(result.getDateOfIssuance()))
                .setDocumentNumber(result.getDocumentNumber())
                .setEyeColor(result.getEyeColour())
                .setLastName(result.getGivenName())
                .setHeight(result.getHeight())
                .setIssuingAuthority(result.getIssuingAuthority())
                .setNationality(result.getNationality())
                .setPlaceOfBirth(result.getPlaceOfBirth())
                .setResidence(result.getPrincipalResidence())
                .setSex(result.getSex())
                .setFirstName(result.getSurname())
                .setMrzVerified(result.isMrtdVerified())
                .setEncodedFaceImage(ModelConverter.encodeImage(result.getFaceImage()))
                .setScanningFirstSideDone(result.isScanningFirstSideDone())
                .setDocumentDataMatch(result.isDocumentDataMatch())
                .setDigitalSignatureVersion(result.getDigitalSignatureVersion())
                .setDigitalSignature(result.getDigitalSignature());
    }

    private static DocumentScannerResult parseUAEBackSide(@NonNull UnitedArabEmiratesIdBackRecognizer.Result result) {
        return new UAEBackSideResult(result.getResultState())
                .setEncodedFullDocumentImage(result.getEncodedFullDocumentImage())
                .setMrzVerified(result.getMrzResult().isMrzVerified())
                .setSecondaryId(result.getMrzResult().getSecondaryId())
                .setPrimaryId(result.getMrzResult().getPrimaryId())
                .setOpt2(result.getMrzResult().getOpt2())
                .setOpt1(result.getMrzResult().getOpt1())
                .setApplicationReceiptNumber(result.getMrzResult().getApplicationReceiptNumber())
                .setNationality(result.getMrzResult().getNationality())
                .setDocumentType(result.getMrzResult().getDocumentType().name())
                .setDocumentCode(result.getMrzResult().getDocumentCode())
                .setDateOfExpiry(ModelConverter.convertDate(result.getMrzResult().getDateOfExpiry()))
                .setDateOfBirth(ModelConverter.convertDate(result.getMrzResult().getDateOfBirth()))
                .setIssuer(result.getMrzResult().getIssuer())
                .setDocumentNumber(result.getMrzResult().getDocumentNumber())
                .setSex(result.getMrzResult().getGender())
                .setMrzText(result.getMrzResult().getMrzText())
                .setAlienNr(result.getMrzResult().getAlienNumber())
                .setImmigrantCaseNumber(result.getMrzResult().getImmigrantCaseNumber());
    }

    private static DocumentScannerResult parseUAEFrontSide(@NonNull UnitedArabEmiratesIdFrontRecognizer.Result result) {
        return new UAEFrontSideResult(result.getResultState())
                .setEncodedFaceImage(ModelConverter.encodeImage(result.getFaceImage()))
                .setEncodedFullDocumentImage(result.getEncodedFullDocumentImage())
                .setIdentityCardNumber(result.getIdNumber())
                .setName(result.getName())
                .setNationality(result.getNationality());
    }

    private static DocumentScannerResult parseSwissBackSide(@NonNull SwitzerlandIdBackRecognizer.Result result) {
        return new SwissBackSideResult(result.getResultState())
                .setIssuingAuthority(result.getAuthority())
                .setDateOfBirth(ModelConverter.convertDate(result.getMrzResult().getDateOfBirth()))
                .setDateOfExpiry(ModelConverter.convertDate(result.getDateOfExpiry()))
                .setDateOfIssue(ModelConverter.convertDate(result.getDateOfIssue()))
                .setDocumentCode(result.getMrzResult().getDocumentCode())
                .setDocumentNumber(result.getMrzResult().getDocumentNumber())
                .setHeight(result.getHeight())
                .setIssuer(result.getMrzResult().getIssuer())
                .setMrzText(result.getMrzResult().getMrzText())
                .setNationality(result.getMrzResult().getNationality())
                .setOpt1(result.getMrzResult().getOpt1())
                .setOpt2(result.getMrzResult().getOpt2())
                .setPlaceOfBirth(result.getPlaceOfOrigin())
                .setPrimaryId(result.getMrzResult().getPrimaryId())
                .setSecondaryId(result.getMrzResult().getSecondaryId())
                .setSex(result.getSex())
                .setMrzVerified(result.getMrzResult().isMrzVerified());
    }

    private static DocumentScannerResult parseSwissFrontSide(@NonNull SwitzerlandIdFrontRecognizer.Result result) {
        return new SwissFrontSideResult(result.getResultState())
                .setDateOfBirth(ModelConverter.convertDate(result.getDateOfBirth()))
                .setFirstName(result.getSurname())
                .setLastName(result.getGivenName())
                .setEncodedFaceImage(ModelConverter.encodeImage(result.getFaceImage()));
    }

    private static DocumentScannerResult parseSloveniaCombined(@NonNull SloveniaCombinedRecognizer.Result result) {
        return new SloveniaCombinedResult(result.getResultState())
                .setDateOfBirth(ModelConverter.convertDate(result.getDateOfBirth()))
                .setDateOfExpiry(ModelConverter.convertDate(result.getDateOfExpiry()))
                .setDateOfIssue(ModelConverter.convertDate(result.getDateOfIssue()))
                .setSex(result.getSex())
                .setTitle(result.getTitle())
                .setMrzVerified(result.isMrzVerified())
                .setAddress(result.getAddress())
                .setCitizenship(result.getCitizenship())
                .setEncodedSignatureImage(result.getEncodedSignatureImage())
                .setFirstName(result.getFirstName())
                .setIdentityCardNumber(result.getIdentityCardNumber())
                .setIssuingAuthority(result.getIssuingAuthority())
                .setLastName(result.getLastName())
                .setPersonalIdentificationNumber(result.getPersonalIdentificationNumber())
                .setSex(result.getSex())
                .setTitle(result.getTitle())
                .setMrzVerified(result.isMrzVerified())
                .setEncodedFaceImage(ModelConverter.encodeImage(result.getFaceImage()))
                .setDigitalSignature(result.getDigitalSignature())
                .setDigitalSignatureVersion(result.getDigitalSignatureVersion())
                .setEncodedBackFullDocumentImage(result.getEncodedBackFullDocumentImage())
                .setEncodedFrontFullDocumentImage(result.getEncodedFrontFullDocumentImage())
                .setScanningFirstSideDone(result.isScanningFirstSideDone())
                .setDocumentDataMatch(result.isDocumentDataMatch());
    }

    private static DocumentScannerResult parseSloveniaBackSide(@NonNull SloveniaIdBackRecognizer.Result result) {
        return new SloveniaBackSideResult(result.getResultState())
                .setDateOfBirth(ModelConverter.convertDate(result.getDateOfBirth()))
                .setDateOfExpiry(ModelConverter.convertDate(result.getDateOfExpiry()))
                .setDateOfIssue(ModelConverter.convertDate(result.getDateOfIssue()))
                .setDocumentNumber(result.getDocumentNumber())
                .setNationality(result.getNationality())
                .setSex(result.getSex())
                .setTitle(result.getTitle())
                .setMrzVerified(result.isMrzVerified())
                .setAddress(result.getAddress())
                .setIssuingAuthority(result.getAuthority())
                .setDocumentCode(result.getDocumentCode())
                .setIssuer(result.getIssuer())
                .setMrzText(result.getMrzText())
                .setOpt1(result.getOpt1())
                .setOpt2(result.getOpt2())
                .setPrimaryId(result.getPrimaryId())
                .setSecondaryId(result.getSecondaryId());
    }

    private static DocumentScannerResult parseSloveniaFrontSide(@NonNull SloveniaIdFrontRecognizer.Result result) {
        return new SloveniaFrontSideResult(result.getResultState())
                .setDateOfBirth(ModelConverter.convertDate(result.getDateOfBirth()))
                .setDateOfExpiry(ModelConverter.convertDate(result.getDateOfExpiry()))
                .setFirstName(result.getFirstName())
                .setEncodedFaceImage(ModelConverter.encodeImage(result.getFaceImage()))
                .setLastName(result.getLastName())
                .setNationality(result.getNationality())
                .setSex(result.getSex())
                .setTitle(result.getTitle());
    }

    private static DocumentScannerResult parseSlovakiaCombined(@NonNull SlovakiaCombinedRecognizer.Result result) {
        return new SlovakiaCombinedResult(result.getResultState())
                .setAddress(result.getAddress())
                .setDateOfBirth(ModelConverter.convertDate(result.getDateOfBirth()))
                .setDateOfExpiry(ModelConverter.convertDate(result.getDateOfExpiry()))
                .setDateOfIssue(ModelConverter.convertDate(result.getDateOfIssue()))
                .setDocumentNumber(result.getDocumentNumber())
                .setPersonalIdentificationNumber(result.getPersonalIdentificationNumber())
                .setEncodedSignatureImage(result.getEncodedSignatureImage())
                .setFirstName(result.getFirstName())
                .setIssuingAuthority(result.getIssuingAuthority())
                .setLastName(result.getLastName())
                .setNationality(result.getNationality())
                .setPlaceOfBirth(result.getPlaceOfBirth())
                .setSex(result.getSex())
                .setSurnameAtBirth(result.getSurnameAtBirth())
                .setSpecialRemarks(result.getSpecialRemarks())
                .setTitle(result.getTitle())
                .setMrzVerified(result.isMrzVerified())
                .setEncodedFaceImage(ModelConverter.encodeImage(result.getFaceImage()))
                .setDigitalSignature(result.getDigitalSignature())
                .setDigitalSignatureVersion(result.getDigitalSignatureVersion())
                .setEncodedBackFullDocumentImage(result.getEncodedBackFullDocumentImage())
                .setEncodedFrontFullDocumentImage(result.getEncodedFrontFullDocumentImage())
                .setScanningFirstSideDone(result.isScanningFirstSideDone())
                .setDocumentDataMatch(result.isDocumentDataMatch());
    }

    private static DocumentScannerResult parseSlovakiaBackSide(@NonNull SlovakiaIdBackRecognizer.Result result) {
        return new SlovakiaBackSideResult(result.getResultState())
                .setMrzText(result.getMrzResult().getMrzText())
                .setAddress(result.getAddress())
                .setDateOfBirth(ModelConverter.convertDate(result.getMrzResult().getDateOfBirth()))
                .setDateOfExpiry(ModelConverter.convertDate(result.getMrzResult().getDateOfExpiry()))
                .setDocumentCode(result.getMrzResult().getDocumentCode())
                .setDocumentNumber(result.getMrzResult().getDocumentNumber())
                .setIssuer(result.getMrzResult().getIssuer())
                .setNationality(result.getMrzResult().getNationality())
                .setOpt1(result.getMrzResult().getOpt1())
                .setOpt2(result.getMrzResult().getOpt2())
                .setSurnameAtBirth(result.getSurnameAtBirth())
                .setPlaceOfBirth(result.getPlaceOfBirth())
                .setPrimaryId(result.getMrzResult().getPrimaryId())
                .setSecondaryId(result.getMrzResult().getSecondaryId())
                .setSex(result.getMrzResult().getGender())
                .setSpecialRemarks(result.getSpecialRemarks())
                .setMrzVerified(result.getMrzResult().isMrzVerified());
    }

    private static DocumentScannerResult parseSlovakiaFrontSide(@NonNull SlovakiaIdFrontRecognizer.Result result) {
        return new SlovakiaFrontSideResult(result.getResultState())
                .setDocumentNumber(result.getDocumentNumber())
                .setDateOfBirth(ModelConverter.convertDate(result.getDateOfBirth()))
                .setDateOfExpiry(ModelConverter.convertDate(result.getDateOfExpiry()))
                .setDateOfIssue(ModelConverter.convertDate(result.getDateOfIssue()))
                .setFirstName(result.getFirstName())
                .setEncodedFaceImage(ModelConverter.encodeImage(result.getFaceImage()))
                .setIssuer(result.getIssuedBy())
                .setLastName(result.getLastName())
                .setNationality(result.getNationality())
                .setPersonalIdentificationNumber(result.getPersonalNumber())
                .setSex(result.getSex());
    }

    private static DocumentScannerResult parseSingaporeCombined(@NonNull SingaporeCombinedRecognizer.Result result) {
        return new SingaporeCombinedResult(result.getResultState())
                .setAddress(result.getAddress())
                .setBloodType(result.getBloodGroup())
                .setCardNumber(result.getIdentityCardNumber())
                .setPlaceOfBirth(result.getCountryOfBirth())
                .setDateOfBirth(ModelConverter.convertDate(result.getDateOfBirth()))
                .setDateOfIssue(ModelConverter.convertDate(result.getDateOfIssue()))
                .setName(result.getName())
                .setRace(result.getRace())
                .setSex(result.getSex())
                .setEncodedFaceImage(ModelConverter.encodeImage(result.getFaceImage()))
                .setDigitalSignature(result.getDigitalSignature())
                .setDigitalSignatureVersion(result.getDigitalSignatureVersion())
                .setEncodedBackFullDocumentImage(result.getEncodedBackFullDocumentImage())
                .setEncodedFrontFullDocumentImage(result.getEncodedFrontFullDocumentImage())
                .setScanningFirstSideDone(result.isScanningFirstSideDone())
                .setDocumentDataMatch(result.isDocumentDataMatch());
    }

    private static DocumentScannerResult parseSingaporeBackSide(@NonNull SingaporeIdBackRecognizer.Result result) {
        return new SingaporeBackSideResult(result.getResultState())
                .setAddress(result.getAddress())
                .setBloodType(result.getBloodGroup())
                .setCardNumber(result.getCardNumber())
                .setDateOfIssue(ModelConverter.convertDate(result.getDateOfIssue()));
    }

    private static DocumentScannerResult parseSingaporeFrontSide(@NonNull SingaporeIdFrontRecognizer.Result result) {
        return new SingaporeFrontSideResult(result.getResultState())
                .setCardNumber(result.getIdentityCardNumber())
                .setPlaceOfBirth(result.getCountryOfBirth())
                .setDateOfBirth(ModelConverter.convertDate(result.getDateOfBirth()))
                .setName(result.getName())
                .setEncodedFaceImage(ModelConverter.encodeImage(result.getFaceImage()))
                .setRace(result.getRace())
                .setSex(result.getSex());
    }

    private static DocumentScannerResult parseSerbiaCombined(@NonNull SerbiaCombinedRecognizer.Result result) {
        return new SerbiaCombinedResult(result.getResultState())
                .setDateOfBirth(ModelConverter.convertDate(result.getDateOfBirth()))
                .setDateOfExpiry(ModelConverter.convertDate(result.getDateOfExpiry()))
                .setDateOfIssue(ModelConverter.convertDate(result.getDateOfIssue()))
                .setEncodedSignatureImage(result.getEncodedSignatureImage())
                .setFirstName(result.getFirstName())
                .setIdentityCardNumber(result.getIdentityCardNumber())
                .setIssuer(result.getIssuer())
                .setJmbg(result.getJmbg())
                .setLastName(result.getLastName())
                .setNationality(result.getNationality())
                .setSex(result.getSex())
                .setTitle(result.getTitle())
                .setMrzVerified(result.isMrzVerified())
                .setEncodedFaceImage(ModelConverter.encodeImage(result.getFaceImage()))
                .setDigitalSignature(result.getDigitalSignature())
                .setDigitalSignatureVersion(result.getDigitalSignatureVersion())
                .setEncodedBackFullDocumentImage(result.getEncodedBackFullDocumentImage())
                .setEncodedFrontFullDocumentImage(result.getEncodedFrontFullDocumentImage())
                .setScanningFirstSideDone(result.isScanningFirstSideDone())
                .setDocumentDataMatch(result.isDocumentDataMatch());
    }

    private static DocumentScannerResult parseSerbiaBackSide(@NonNull SerbiaIdBackRecognizer.Result result) {
        return new SerbiaBackSideResult(result.getResultState())
                .setDateOfBirth(ModelConverter.convertDate(result.getDateOfBirth()))
                .setDateOfExpiry(ModelConverter.convertDate(result.getDateOfExpiry()))
                .setDocumentCode(result.getDocumentCode())
                .setDocumentNumber(result.getDocumentNumber())
                .setIssuer(result.getIssuer())
                .setMrzText(result.getMrzText())
                .setNationality(result.getNationality())
                .setOpt1(result.getOpt1())
                .setOpt2(result.getOpt2())
                .setPrimaryId(result.getPrimaryId())
                .setSecondaryId(result.getSecondaryId())
                .setSex(result.getSex())
                .setTitle(result.getTitle())
                .setMrzVerified(result.isMrzVerified());
    }

    private static DocumentScannerResult parseSerbiaFrontSide(@NonNull SerbiaIdFrontRecognizer.Result result) {
        return new SerbiaFrontSideResult(result.getResultState())
                .setDocumentNumber(result.getDocumentNumber())
                .setDateOfIssue(ModelConverter.convertDate(result.getIssuingDate()))
                .setEncodedFaceImage(ModelConverter.encodeImage(result.getFaceImage()))
                .setTitle(result.getTitle())
                .setDateOfExpiry(ModelConverter.convertDate(result.getValidUntil()));
    }

    private static DocumentScannerResult parseRomaniaFrontSide(@NonNull RomaniaIdFrontRecognizer.Result result) {
        return new RomaniaFrontSideResult(result.getResultState())
                .setMrzText(result.getMrzText())
                .setDocumentNumber(result.getDocumentNumber())
                .setCardNumber(result.getCardNumber())
                .setSeries(result.getIdSeries())
                .setSecondaryId(result.getSecondaryId())
                .setMrzVerified(result.isMrzVerified())
                .setPlaceOfBirth(result.getPlaceOfBirth())
                .setParentsName(result.getParentNames())
                .setPersonalIdentificationNumber(result.getCnp())
                .setAddress(result.getAddress())
                .setDateOfBirth(ModelConverter.convertDate(result.getDateOfBirth()))
                .setDateOfExpiry(ModelConverter.convertDate(result.getValidUntil()))
                .setDocumentCode(result.getDocumentCode())
                .setFirstName(result.getFirstName())
                .setIssuingAuthority(result.getIssuedBy())
                .setEncodedFaceImage(ModelConverter.encodeImage(result.getFaceImage()))
                .setIssuer(result.getIssuer())
                .setLastName(result.getLastName())
                .setNationality(result.getNationality())
                .setOpt1(result.getOpt1())
                .setOpt2(result.getOpt2())
                .setSex(result.getSex())
                .setPrimaryId(result.getPrimaryId())
                .setTitle(result.getTitle())
                .setDateOfIssue(ModelConverter.convertDate(result.getValidFrom()));
    }

    private static DocumentScannerResult parsePolandCombined(@NonNull PolandCombinedRecognizer.Result result) {
        return new PolandCombinedResult(result.getResultState())
                .setDateOfBirth(ModelConverter.convertDate(result.getDateOfBirth()))
                .setDateOfExpiry(ModelConverter.convertDate(result.getDateOfExpiry()))
                .setDocumentNumber(result.getDocumentNumber())
                .setLastName(result.getFamilyName())
                .setParentsGivenNames(result.getParentsGivenNames())
                .setFirstName(result.getGivenNames())
                .setIssuer(result.getIssuer())
                .setNationality(result.getNationality())
                .setPersonalIdentificationNumber(result.getPersonalNumber())
                .setSex(result.getSex())
                .setSurname(result.getSurname())
                .setTitle(result.getTitle())
                .setMrzVerified(result.isMrzVerified())
                .setEncodedFaceImage(ModelConverter.encodeImage(result.getFaceImage()))
                .setDigitalSignature(result.getDigitalSignature())
                .setDigitalSignatureVersion(result.getDigitalSignatureVersion())
                .setEncodedBackFullDocumentImage(result.getEncodedBackFullDocumentImage())
                .setEncodedFrontFullDocumentImage(result.getEncodedFrontFullDocumentImage())
                .setScanningFirstSideDone(result.isScanningFirstSideDone())
                .setDocumentDataMatch(result.isDocumentDataMatch());
    }

    private static DocumentScannerResult parsePolandBackSide(@NonNull PolandIdBackRecognizer.Result result) {
        return new PolandBackSideResult(result.getResultState())
                .setDateOfBirth(ModelConverter.convertDate(result.getDateOfBirth()))
                .setDateOfExpiry(ModelConverter.convertDate(result.getDateOfExpiry()))
                .setDocumentCode(result.getDocumentCode())
                .setDocumentNumber(result.getDocumentNumber())
                .setIssuer(result.getIssuer())
                .setMrzText(result.getMrzText())
                .setNationality(result.getNationality())
                .setOpt1(result.getOpt1())
                .setOpt2(result.getOpt2())
                .setPrimaryId(result.getPrimaryId())
                .setSecondaryId(result.getSecondaryId())
                .setSex(result.getSex())
                .setTitle(result.getTitle())
                .setMrzVerified(result.isMrzVerified());
    }

    private static DocumentScannerResult parsePolandFrontSide(@NonNull PolandIdFrontRecognizer.Result result) {
        return new PolandFrontSideResult(result.getResultState())
                .setDateOfBirth(ModelConverter.convertDate(result.getDateOfBirth()))
                .setLastName(result.getFamilyName())
                .setGivenName(result.getGivenNames())
                .setFirstName(result.getSurname())
                .setParentsGivenName(result.getParentsGivenNames())
                .setSex(result.getSex())
                .setEncodedFaceImage(ModelConverter.encodeImage(result.getFaceImage()))
                .setTitle(result.getTitle());
    }

    private static DocumentScannerResult parseMalaysiaFrontSide(@NonNull MalaysiaMyTenteraFrontRecognizer.Result result) {
        return new MalaysiaMyTenteraFrontSideResult(result.getResultState())
                .setArmyNumber(result.getArmyNumber())
                .setNricNumber(result.getNric())
                .setAddress(result.getFullAddress())
                .setAddressCity(result.getCity())
                .setAddressState(result.getOwnerState())
                .setAddressStreet(result.getStreet())
                .setAddressZipCode(result.getZipcode())
                .setDateOfBirth(ModelConverter.convertDate(result.getBirthDate()))
                .setEncodedFaceImage(ModelConverter.encodeImage(result.getFaceImage()))
                .setReligion(result.getReligion())
                .setFullName(result.getFullName())
                .setSex(result.getSex());
    }

    private static MyKadBackSideResult parseMyKadBackSide(@NonNull MalaysiaMyKadBackRecognizer.Result result) {
        return new MyKadBackSideResult(result.getResultState())
                .setDateOfBirth(result.getDateOfBirth().getDate())
                .setEncodedFullDocumentImage(result.getEncodedFullDocumentImage())
                .setExtendedNric(result.getExtendedNric())
                .setNric(result.getNric())
                .setOldNric(result.getOldNric());
    }

    private static DocumentScannerResult parseMyKadFrontSide(@NonNull MalaysiaMyKadFrontRecognizer.Result result) {
        return new MyKadFrontSideResult(result.getResultState())
                .setAddress(result.getFullAddress())
                .setAddressState(result.getOwnerState())
                .setAddressCity(result.getCity())
                .setAddressZipCode(result.getZipcode())
                .setAddressStreet(result.getStreet())
                .setNricNumber(result.getNric())
                .setEncodedFaceImage(ModelConverter.encodeImage(result.getFaceImage()))
                .setDateOfBirth(ModelConverter.convertDate(result.getBirthDate()))
                .setFullName(result.getFullName())
                .setReligion(result.getReligion())
                .setSex(result.getSex());
    }

    private static DocumentScannerResult parseMrtdCombined(@NonNull MrtdCombinedRecognizer.Result result) {
        return new MRZCombinedResult(result.getResultState())
                .setMrzText(result.getMrzResult().getMrzText())
                .setImmigrationCaseNumber(result.getMrzResult().getImmigrantCaseNumber())
                .setApplicationReceiptNumber(result.getMrzResult().getApplicationReceiptNumber())
                .setAlienNumber(result.getMrzResult().getAlienNumber())
                .setDocumentNumber(result.getMrzResult().getDocumentNumber())
                .setDateOfBirth(ModelConverter.convertDate(result.getMrzResult().getDateOfBirth()))
                .setDateOfExpiry(ModelConverter.convertDate(result.getMrzResult().getDateOfExpiry()))
                .setDocumentCode(result.getMrzResult().getDocumentCode())
                .setIssuer(result.getMrzResult().getIssuer())
                .setNationality(result.getMrzResult().getNationality())
                .setOpt1(result.getMrzResult().getOpt1())
                .setGender(result.getMrzResult().getGender())
                .setOpt2(result.getMrzResult().getOpt2())
                .setPrimaryId(result.getMrzResult().getPrimaryId())
                .setSecondaryId(result.getMrzResult().getSecondaryId())
                .setMrzVerified(result.getMrzResult().isMrzVerified())
                .setEncodedFaceImage(ModelConverter.encodeImage(result.getFaceImage()))
                .setDigitalSignature(result.getDigitalSignature())
                .setDigitalSignatureVersion(result.getDigitalSignatureVersion())
                .setEncodedBackFullDocumentImage(result.getEncodedBackFullDocumentImage())
                .setEncodedFrontFullDocumentImage(result.getEncodedFrontFullDocumentImage())
                .setScanningFirstSideDone(result.isScanningFirstSideDone())
                .setDocumentDataMatch(result.isDocumentDataMatch());
    }

    private static DocumentScannerResult parseMrtd(@NonNull MrtdRecognizer.Result result) {
        MrzResult mrzResult = result.getMrzResult();
        return new MRZResult(result.getResultState())
                .setMrzText(mrzResult.getMrzText())
                .setImmigrationCaseNumber(mrzResult.getImmigrantCaseNumber())
                .setApplicationReceiptNumber(mrzResult.getApplicationReceiptNumber())
                .setAlienNumber(mrzResult.getAlienNumber())
                .setSex(mrzResult.getGender())
                .setDocumentNumber(mrzResult.getDocumentNumber())
                .setDateOfBirth(ModelConverter.convertDate(mrzResult.getDateOfBirth().getDate()))
                .setDateOfExpiry(ModelConverter.convertDate(mrzResult.getDateOfExpiry().getDate()))
                .setDocumentCode(mrzResult.getDocumentCode())
                .setIssuer(mrzResult.getIssuer())
                .setNationality(mrzResult.getNationality())
                .setOpt1(mrzResult.getOpt1())
                .setDocumentType(mrzResult.getDocumentType().name())
                .setOpt2(mrzResult.getOpt2())
                .setPrimaryId(mrzResult.getPrimaryId())
                .setSecondaryId(mrzResult.getSecondaryId())
                .setMrzVerified(mrzResult.isMrzVerified());
    }

    private static DocumentScannerResult parseJordanCombined(@NonNull JordanCombinedRecognizer.Result result) {
        return new JordanCombinedResult(result.getResultState())
                .setDateOfBirth(ModelConverter.convertDate(result.getDateOfBirth()))
                .setDateOfExpiry(ModelConverter.convertDate(result.getDateOfExpiry()))
                .setIssuer(result.getIssuer())
                .setName(result.getName())
                .setNationality(result.getNationality())
                .setNationalNumber(result.getNationalNumber())
                .setSex(result.getSex())
                .setTitle(result.getTitle())
                .setMrzVerified(result.isMrzVerified())
                .setDocumentNumber(result.getDocumentNumber())
                .setEncodedFaceImage(ModelConverter.encodeImage(result.getFaceImage()))
                .setDigitalSignature(result.getDigitalSignature())
                .setDigitalSignatureVersion(result.getDigitalSignatureVersion())
                .setEncodedBackFullDocumentImage(result.getEncodedBackFullDocumentImage())
                .setEncodedFrontFullDocumentImage(result.getEncodedFrontFullDocumentImage())
                .setScanningFirstSideDone(result.isScanningFirstSideDone())
                .setDocumentDataMatch(result.isDocumentDataMatch());
    }

    private static DocumentScannerResult parseJordanBackSide(@NonNull JordanIdBackRecognizer.Result result) {
        return new JordanBackSideResult(result.getResultState())
                .setDateOfBirth(ModelConverter.convertDate(result.getDateOfBirth()))
                .setDateOfExpiry(ModelConverter.convertDate(result.getDateOfExpiry()))
                .setDocumentCode(result.getDocumentCode())
                .setDocumentNumber(result.getDocumentNumber())
                .setIssuer(result.getIssuer())
                .setMrzText(result.getMrzText())
                .setNationality(result.getNationality())
                .setOpt1(result.getOpt1())
                .setOpt2(result.getOpt2())
                .setPrimaryId(result.getPrimaryId())
                .setSecondaryId(result.getSecondaryId())
                .setSex(result.getSex())
                .setTitle(result.getTitle())
                .setMrzVerified(result.isMrzVerified());
    }

    private static DocumentScannerResult parseJordanFrontSide(@NonNull JordanIdFrontRecognizer.Result result) {
        return new JordanFrontSideResult(result.getResultState())
                .setDateOfBirth(ModelConverter.convertDate(result.getDateOfBirth()))
                .setNationalNumber(result.getNationalNumber())
                .setSex(result.getSex())
                .setTitle(result.getTitle())
                .setEncodedFaceImage(ModelConverter.encodeImage(result.getFaceImage()))
                .setName(result.getName());
    }

    private static DocumentScannerResult parseIndonesiaFrontSide(@NonNull IndonesiaIdFrontRecognizer.Result result) {
        return new IndonesiaFrontSideResult(result.getResultState())
                .setAddress(result.getAddress())
                .setBloodType(result.getBloodType())
                .setCitizenship(result.getCitizenship())
                .setCity(result.getCity())
                .setKelDesa(result.getKelDesa())
                .setMaritalStatus(result.getMaritalStatus())
                .setDistrict(result.getDistrict())
                .setDateOfBirth(ModelConverter.convertDate(result.getDateOfBirth()))
                .setDocumentNumber(result.getDocumentNumber())
                .setDateOfExpiry(ModelConverter.convertDate(result.getDateOfExpiry()))
                .setName(result.getName())
                .setRt(result.getRt())
                .setValidUntilPermanent(result.isDateOfExpiryPermanent())
                .setRw(result.getRw())
                .setEncodedFaceImage(ModelConverter.encodeImage(result.getFaceImage()))
                .setSex(result.getSex())
                .setReligion(result.getReligion())
                .setProvince(result.getProvince())
                .setOccupation(result.getOccupation())
                .setPlaceOfBirth(result.getPlaceOfBirth());
    }

    private static DocumentScannerResult parseIkad(@NonNull MalaysiaIkadFrontRecognizer.Result result) {
        return new IkadFrontSideResult(result.getResultState())
                .setAddress(result.getAddress())
                .setEmployer(result.getEmployer())
                .setFacultyAddress(result.getFacultyAddress())
                .setDateOfBirth(ModelConverter.convertDate(result.getDateOfBirth()))
                .setDateOfExpiry(ModelConverter.convertDate(result.getDateOfExpiry()))
                .setName(result.getName())
                .setPassportNumber(result.getPassportNumber())
                .setSector(result.getSector())
                .setNationality(result.getNationality())
                .setEncodedFaceImage(ModelConverter.encodeImage(result.getFaceImage()))
                .setSex(result.getGender());
    }

    private static DocumentScannerResult parseHongKongFrontSide(@NonNull HongKongIdFrontRecognizer.Result result) {
        return new HongKongFrontSideResult(result.getResultState())
                .setCommercialCode(result.getCommercialCode())
                .setDateOfBirth(ModelConverter.convertDate(result.getDateOfBirth()))
                .setDateOfIssue(ModelConverter.convertDate(result.getDateOfIssue()))
                .setDocumentNumber(result.getDocumentNumber())
                .setFullName(result.getFullName())
                .setResidentialStatus(result.getResidentialStatus())
                .setEncodedFaceImage(ModelConverter.encodeImage(result.getFaceImage()))
                .setSex(result.getSex());
    }

    private static DocumentScannerResult parseGermanyOld(@NonNull GermanyIdOldRecognizer.Result result) {
        return new GermanyOldResult(result.getResultState())
                .setDateOfBirth(ModelConverter.convertDate(result.getMrzResult().getDateOfBirth()))
                .setDateOfExpiry(ModelConverter.convertDate(result.getMrzResult().getDateOfExpiry()))
                .setDocumentCode(result.getMrzResult().getDocumentCode())
                .setDocumentNumber(result.getMrzResult().getDocumentNumber())
                .setIssuer(result.getMrzResult().getIssuer())
                .setMrzText(result.getMrzResult().getMrzText())
                .setNationality(result.getMrzResult().getNationality())
                .setOpt1(result.getMrzResult().getOpt1())
                .setOpt2(result.getMrzResult().getOpt2())
                .setPlaceOfBirth(result.getPlaceOfBirth())
                .setPrimaryId(result.getMrzResult().getPrimaryId())
                .setSecondaryId(result.getMrzResult().getSecondaryId())
                .setEncodedFaceImage(ModelConverter.encodeImage(result.getFaceImage()))
                .setSex(result.getMrzResult().getGender())
                .setMrzVerified(result.getMrzResult().isMrzVerified());
    }

    private static DocumentScannerResult parseGermanyCombined(@NonNull GermanyCombinedRecognizer.Result result) {
        return new GermanyCombinedResult(result.getResultState())
                .setIdentityCardNumber(result.getIdentityCardNumber())
                .setCanNumber(result.getCanNumber())
                .setLastName(result.getLastName())
                .setFirstName(result.getFirstName())
                .setHeight(result.getHeight())
                .setAddress(result.getAddress())
                .setDateOfBirth(ModelConverter.convertDate(result.getDateOfBirth()))
                .setDateOfExpiry(ModelConverter.convertDate(result.getDateOfExpiry()))
                .setDateOfIssue(ModelConverter.convertDate(result.getDateOfIssue()))
                .setEncodedSignatureImage(result.getEncodedSignatureImage())
                .setEyeColor(result.getEyeColor())
                .setIssuingAuthority(result.getIssuingAuthority())
                .setNationality(result.getNationality())
                .setPlaceOfBirth(result.getPlaceOfBirth())
                .setSex(result.getSex())
                .setTitle(result.getTitle())
                .setMrzVerified(result.isMrzVerified())
                .setEncodedFaceImage(ModelConverter.encodeImage(result.getFaceImage()))
                .setDigitalSignature(result.getDigitalSignature())
                .setDigitalSignatureVersion(result.getDigitalSignatureVersion())
                .setEncodedBackFullDocumentImage(result.getEncodedBackFullDocumentImage())
                .setEncodedFrontFullDocumentImage(result.getEncodedFrontFullDocumentImage())
                .setScanningFirstSideDone(result.isScanningFirstSideDone())
                .setDocumentDataMatch(result.isDocumentDataMatch());
    }

    private static DocumentScannerResult parseGermanyBackSide(@NonNull GermanyIdBackRecognizer.Result result) {
        return new GermanyBackSideResult(result.getResultState())
                .setAddress(result.getFullAddress())
                .setAddressCity(result.getAddressCity())
                .setAddressStreet(result.getAddressStreet())
                .setAddressHouseNumber(result.getAddressHouseNumber())
                .setAddressZipCode(result.getAddressZipCode())
                .setIssuingAuthority(result.getAuthority())
                .setDateOfBirth(ModelConverter.convertDate(result.getMrzResult().getDateOfBirth()))
                .setDateOfExpiry(ModelConverter.convertDate(result.getMrzResult().getDateOfExpiry()))
                .setDateOfIssue(ModelConverter.convertDate(result.getDateOfIssue()))
                .setDocumentCode(result.getMrzResult().getDocumentCode())
                .setDocumentNumber(result.getMrzResult().getDocumentNumber())
                .setEyeColour(result.getColourOfEyes())
                .setHeight(result.getHeight())
                .setIssuer(result.getMrzResult().getIssuer())
                .setMrzText(result.getMrzResult().getMrzText())
                .setNationality(result.getMrzResult().getNationality())
                .setOpt1(result.getMrzResult().getOpt1())
                .setOpt2(result.getMrzResult().getOpt2())
                .setPrimaryId(result.getMrzResult().getPrimaryId())
                .setSecondaryId(result.getMrzResult().getSecondaryId())
                .setSex(result.getMrzResult().getGender())
                .setMrzVerified(result.getMrzResult().isMrzVerified());
    }

    private static DocumentScannerResult parseGermanyFrontSide(@NonNull GermanyIdFrontRecognizer.Result result) {
        return new GermanyFrontSideResult(result.getResultState())
                .setCanNumber(result.getCanNumber())
                .setDateOfBirth(ModelConverter.convertDate(result.getDateOfBirth()))
                .setDateOfExpiry(ModelConverter.convertDate(result.getDateOfExpiry()))
                .setDocumentNumber(result.getDocumentNumber())
                .setGivenNames(result.getGivenNames())
                .setSurname(result.getSurname())
                .setNationality(result.getNationality())
                .setEncodedFaceImage(ModelConverter.encodeImage(result.getFaceImage()))
                .setPlaceOfBirth(result.getPlaceOfBirth());
    }

    private static DocumentScannerResult parseEgyptFrontSide(@NonNull EgyptIdFrontRecognizer.Result result) {
        return new EgyptFrontSideResult(result.getResultState())
                .setDocumentNumber(result.getDocumentNumber())
                .setNationalNumber(result.getNationalNumber())
                .setEncodedFaceImage(ModelConverter.encodeImage(result.getFaceImage()))
                .setTitle(result.getTitle());
    }

    private static DocumentScannerResult parseCzechCombined(@NonNull CzechiaCombinedRecognizer.Result result) {
        return new CzechCombinedResult(result.getResultState())
                .setPersonalIdentificationNumber(result.getPersonalIdentificationNumber())
                .setAddress(result.getAddress())
                .setDateOfBirth(ModelConverter.convertDate(result.getDateOfBirth()))
                .setDateOfExpiry(ModelConverter.convertDate(result.getDateOfExpiry()))
                .setDateOfIssue(ModelConverter.convertDate(result.getDateOfIssue()))
                .setEncodedSignatureImage(result.getEncodedSignatureImage())
                .setFirstName(result.getFirstName())
                .setIdentityCardNumber(result.getIdentityCardNumber())
                .setIssuingAuthority(result.getIssuingAuthority())
                .setLastName(result.getLastName())
                .setNationality(result.getNationality())
                .setPlaceOfBirth(result.getPlaceOfBirth())
                .setSex(result.getSex())
                .setTitle(result.getTitle())
                .setMrzVerified(result.isMrzVerified())
                .setEncodedFaceImage(ModelConverter.encodeImage(result.getFaceImage()))
                .setDigitalSignature(result.getDigitalSignature())
                .setDigitalSignatureVersion(result.getDigitalSignatureVersion())
                .setEncodedBackFullDocumentImage(result.getEncodedBackFullDocumentImage())
                .setEncodedFrontFullDocumentImage(result.getEncodedFrontFullDocumentImage())
                .setScanningFirstSideDone(result.isScanningFirstSideDone())
                .setDocumentDataMatch(result.isDocumentDataMatch());
    }

    private static DocumentScannerResult parseCzechBackSide(@NonNull CzechiaIdBackRecognizer.Result result) {
        return new CzechBackSideResult(result.getResultState())
                .setMrzText(result.getMrzResult().getMrzText())
                .setPermanentStay(result.getPermanentStay())
                .setDateOfExpiry(ModelConverter.convertDate(result.getMrzResult().getDateOfExpiry()))
                .setIssuingAuthority(result.getAuthority())
                .setDateOfBirth(ModelConverter.convertDate(result.getMrzResult().getDateOfBirth()))
                .setDocumentCode(result.getMrzResult().getDocumentCode())
                .setDocumentNumber(result.getMrzResult().getDocumentNumber())
                .setIssuer(result.getMrzResult().getIssuer())
                .setNationality(result.getMrzResult().getNationality())
                .setOpt1(result.getMrzResult().getOpt1())
                .setOpt2(result.getMrzResult().getOpt2())
                .setPersonalIdentificationNumber(result.getPersonalNumber())
                .setPrimaryId(result.getMrzResult().getPrimaryId())
                .setSecondaryId(result.getMrzResult().getSecondaryId())
                .setSex(result.getMrzResult().getGender())
                .setMrzVerified(result.getMrzResult().isMrzVerified());
    }

    private static DocumentScannerResult parseCzechFrontSide(@NonNull CzechiaIdFrontRecognizer.Result result) {
        return new CzechFrontSideResult(result.getResultState())
                .setDateOfBirth(ModelConverter.convertDate(result.getDateOfBirth()))
                .setDateOfExpiry(ModelConverter.convertDate(result.getDateOfExpiry()))
                .setDateOfIssue(ModelConverter.convertDate(result.getDateOfIssue()))
                .setGivenNames(result.getGivenNames())
                .setSurname(result.getSurname())
                .setPlaceOfBirth(result.getPlaceOfBirth())
                .setSex(result.getSex())
                .setEncodedFaceImage(ModelConverter.encodeImage(result.getFaceImage()))
                .setDocumentNumber(result.getDocumentNumber());
    }

    private static DocumentScannerResult parseCroatiaCombined(@NonNull CroatiaCombinedRecognizer.Result result) {
        return new CroatiaCombinedResult(result.getResultState())
                .setCitizenship(result.getCitizenship())
                .setDateOfBirth(ModelConverter.convertDate(result.getDateOfBirth()))
                .setDateOfExpiry(ModelConverter.convertDate(result.getDateOfExpiry()))
                .setDateOfIssue(ModelConverter.convertDate(result.getDateOfIssue()))
                .setEncodedSignatureImage(result.getEncodedSignatureImage())
                .setFirstName(result.getFirstName())
                .setDocumentNumber(result.getDocumentNumber())
                .setIssuedBy(result.getIssuedBy())
                .setLastName(result.getLastName())
                .setSex(result.getSex())
                .setDateOfExpiryPermanent(result.isDateOfExpiryPermanent())
                .setDocumentBilingual(result.isDocumentBilingual())
                .setMrzVerified(result.isMrzVerified())
                .setDocumentForNonResident(result.isDocumentForNonResident())
                .setOIB(result.getOib())
                .setResidence(result.getResidence())
                .setEncodedFaceImage(ModelConverter.encodeImage(result.getFaceImage()))
                .setDigitalSignature(result.getDigitalSignature())
                .setDigitalSignatureVersion(result.getDigitalSignatureVersion())
                .setEncodedBackFullDocumentImage(result.getEncodedBackFullDocumentImage())
                .setEncodedFrontFullDocumentImage(result.getEncodedFrontFullDocumentImage())
                .setScanningFirstSideDone(result.isScanningFirstSideDone())
                .setDocumentDataMatch(result.isDocumentDataMatch());
    }

    private static DocumentScannerResult parseColombiaBackSide(@NonNull ColombiaIdBackRecognizer.Result result) {
        return new ColombiaBackSideResult(result.getResultState())
                .setBloodGroup(result.getBloodGroup())
                .setDateOfBirth(ModelConverter.convertDate(result.getBirthDate()))
                .setDocumentNumber(result.getDocumentNumber())
                .setFirstName(result.getFirstName())
                .setLastName(result.getLastName())
                .setSex(result.getSex())
                .setFingerprint(result.getFingerprint());
    }

    private static DocumentScannerResult parseColombiaFrontSide(@NonNull ColombiaIdFrontRecognizer.Result result) {
        return new ColombiaFrontSideResult(result.getResultState())
                .setDocumentNumber(result.getDocumentNumber())
                .setEncodedFaceImage(ModelConverter.encodeImage(result.getFaceImage()))
                .setEncodedSignatureImage(result.getEncodedSignatureImage())
                .setEncodedFullDocumentImage(result.getEncodedFullDocumentImage())
                .setFirstName(result.getFirstName())
                .setLastName(result.getLastName());
    }

    private static DocumentScannerResult parseCroatiaFrontSide(@NonNull CroatiaIdFrontRecognizer.Result result) {
        return new CroatiaFrontSideResult(result.getResultState())
                .setCitizenship(result.getCitizenship())
                .setDateOfBirth(ModelConverter.convertDate(result.getDateOfBirth()))
                .setDateOfExpiry(ModelConverter.convertDate(result.getDateOfExpiry()))
                .setFirstName(result.getFirstName())
                .setDocumentNumber(result.getDocumentNumber())
                .setLastName(result.getLastName())
                .setEncodedFaceImage(ModelConverter.encodeImage(result.getFaceImage()))
                .setSex(result.getSex())
                .setDateOfExpiryPermanent(result.isDateOfExpiryPermanent())
                .setDocumentBilingual(result.isDocumentBilingual());
    }

    private static DocumentScannerResult parseCroatiaBackSide(@NonNull CroatiaIdBackRecognizer.Result result) {
        return new CroatiaBackSideResult(result.getResultState())
                .setResidence(result.getResidence())
                .setDateOfBirth(ModelConverter.convertDate(result.getMrzResult().getDateOfBirth()))
                .setDateOfExpiry(ModelConverter.convertDate(result.getMrzResult().getDateOfExpiry()))
                .setDateOfExpiryPermanent(result.isDateOfExpiryPermanent())
                .setDocumentForNonResident(result.isDocumentForNonResident())
                .setDateOfIssue(ModelConverter.convertDate(result.getDateOfIssue()))
                .setDocumentCode(result.getMrzResult().getDocumentCode())
                .setDocumentNumber(result.getMrzResult().getDocumentNumber())
                .setIssuer(result.getMrzResult().getIssuer())
                .setIssuedBy(result.getIssuedBy())
                .setMrzText(result.getMrzResult().getMrzText())
                .setNationality(result.getMrzResult().getNationality())
                .setOpt1(result.getMrzResult().getOpt1())
                .setOpt2(result.getMrzResult().getOpt2())
                .setPrimaryId(result.getMrzResult().getPrimaryId())
                .setSecondaryId(result.getMrzResult().getSecondaryId())
                .setMrzVerified(result.getMrzResult().isMrzVerified());
    }
}
