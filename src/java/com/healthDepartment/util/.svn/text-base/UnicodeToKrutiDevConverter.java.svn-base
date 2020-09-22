/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.healthDepartment.util;

/**
 *
 * @author jpss
 */
public class UnicodeToKrutiDevConverter {

    public String Convert_to_Kritidev_010(String unicodeString){

        if(unicodeString == null || unicodeString.isEmpty())
            return "";
        
        String[] array_one = {
            // ignore all nuktas except in ड़ and ढ़
            "/","‘",   "’",   "“",   "”",   "(",    ")",   "{",    "}",   "=", "।",  "?",  "-",  "µ", "॰", ",", ".", "् ",
            "०",  "१",  "२",  "३",     "४",   "५",  "६",   "७",   "८",   "९", "x",
            
            "फ़्",  "क़",  "ख़",  "ग़", "ज़्", "ज़",  "ड़",  "ढ़",   "फ़",  "य़",  "ऱ",  "ऩ",    // one-byte nukta varNas
            "त्त्",   "त्त",     "क्त",  "दृ",  "कृ",
            
            "ह्न",  "ह्य",  "हृ",  "ह्म",  "ह्र",  "ह्",   "द्द",  "क्ष्", "क्ष", "त्र्", "त्र","ज्ञ",
            "छ्य",  "ट्य",  "ठ्य",  "ड्य",  "ढ्य", "द्य","द्व",
            "श्र",  "ट्र",    "ड्र",    "ढ्र",    "छ्र",   "क्र",  "फ्र",  "द्र",   "प्र",   "ग्र", "रु",  "रू",
            "्र",
            
            "ओ",  "औ",  "आ",   "अ",   "ई",   "इ",  "उ",   "ऊ",  "ऐ",  "ए", "ऋ",

            "क्",  "क",  "क्क",  "ख्",   "ख",    "ग्",   "ग",  "घ्",  "घ",    "ङ",
            "चै",   "च्",   "च",   "छ",  "ज्", "ज",   "झ्",  "झ",   "ञ",

            "ट्ट",   "ट्ठ",   "ट",   "ठ",   "ड्ड",   "ड्ढ",  "ड",   "ढ",  "ण्", "ण",
            "त्",  "त",  "थ्", "थ",  "द्ध",  "द", "ध्", "ध",  "न्",  "न",

            "प्",  "प",  "फ्", "फ",  "ब्",  "ब", "भ्",  "भ",  "म्",  "म",
            "य्",  "य",  "र",  "ल्", "ल",  "ळ",  "व्",  "व",
            "श्", "श",  "ष्", "ष",  "स्",   "स",   "ह",
            
            "ऑ",   "ॉ",  "ो",   "ौ",   "ा",   "ी",   "ु",   "ू",   "ृ",   "े",   "ै",
            "ं",   "ँ",   "ः",   "ॅ",    "ऽ",  "् ", "्" };
        
        String[] array_two = {

            "@","^", "*",  "Þ", "ß", "¼", "½", "¿", "À", "¾", "A", "\\", "&", "&", "Œ", "]","-","~ ",
            "å",  "ƒ",  "„",   "…",   "†",   "‡",   "ˆ",   "‰",   "Š",   "‹","Û",

            "¶",   "d",    "[k",  "x",  "T",  "t",   "M+", "<+", "Q",  ";",    "j",   "u",
            "Ù",   "Ùk",   "ä",    "–",   "—",

            "à",   "á",    "â",   "ã",   "ºz",  "º",   "í", "{", "{k",  "«", "=","K",
            "Nî",   "Vî",    "Bî",   "Mî",   "<î", "|","}",
            "J",   "Vª",   "Mª",  "<ªª",  "Nª",   "Ø",  "Ý",   "æ", "ç", "xz", "#", ":",
            "z",

            "vks",  "vkS",  "vk",    "v",   "bZ",  "b",  "m",  "Å",  ",s",  ",",   "_",
            
            "D",  "d",    "ô",     "[",     "[k",    "X",   "x",  "?",    "?k",   "³",
            "pkS",  "P",    "p",  "N",   "T",    "t",   "÷",  ">",   "¥",
            
            "ê",      "ë",      "V",  "B",   "ì",       "ï",     "M",  "<",  ".", ".k",
            "R",  "r",   "F", "Fk",  ")",    "n", "/",  "/k",  "U", "u",
            
            "I",  "i",   "¶", "Q",   "C",  "c",  "H",  "Hk", "E",   "e",
            "¸",   ";",    "j",  "Y",   "y",  "G",  "O",  "o",
            "'", "'k",  "\"", "\"k", "L",   "l",   "g",

            "v‚",    "‚",    "ks",   "kS",   "k",     "h",    "q",   "w",   "`",    "s",    "S",
            "a",    "¡",    "%",     "W",   "·",   "~ ", "~"};   // "~j"

        //************************************************************
        //Put "Enter chunk size:" line before "<textarea name= ..." if required to be used.
        //************************************************************
        //Enter chunk size: <input type="text" name="chunksize" value="6000" size="7" maxsize="7" style="text-align:right"><br/><br/>
        //************************************************************
        // The following two characters are to be replaced through proper checking of locations:

        // "र्" (reph)
        // "Z" )

        // "ि"
        // "f" )


        int array_one_length = array_one.length ;
        
        String unicode_text = unicodeString;

        //String modified_substring = unicode_text;//document.getElementById("unicode_text").value  ;................
        
        //****************************************************************************************
        //  Break the long text into small bunches of max. max_text_size  characters each.
        //****************************************************************************************
        int text_size = unicode_text.length();//document.getElementById("unicode_text").value.length ;...............

        String processed_text = "" ;  //blank

        int sthiti1 = 0 ;  int sthiti2 = 0 ;  int chale_chalo = 1 ;

        int max_text_size = 6000;

        //************************************************************
        // var max_text_size = chunksize;
        // alert(max_text_size);
        //************************************************************

        while ( chale_chalo == 1 )
        {
            sthiti1 = sthiti2 ;

            if ( sthiti2 < ( text_size - max_text_size ) )
            {
                sthiti2 +=  max_text_size ;
                while (unicode_text.charAt ( sthiti2 ) != ' ') { //document.getElementById("unicode_text").value
                    sthiti2--;
                }
            }
            else  {
                sthiti2 = text_size  ;
                chale_chalo = 0;
            }

            String modified_substring = unicode_text.substring ( sthiti1, sthiti2 )  ;//document.getElementById("unicode_text").value............

            modified_substring = Replace_Symbols(array_one, array_two, modified_substring, array_one_length ) ;

            processed_text += modified_substring ;

            //****************************************************************************************
            //  Breaking part code over
            //****************************************************************************************
            //  processed_text = processed_text.replace( /mangal/g , "Krutidev010" ) ;
            return processed_text;//document.getElementById("legacy_text").value = processed_text  ;..............
        }

        return processed_text;
        //**************************************************


    } // end of Convert_Unicode_to_Krutidev010 function
    
    
    public String Replace_Symbols(String[] array_one, String[] array_two, String modified_substring, int array_one_length  ){

        // if string to be converted is non-blank then no need of any processing.
        if (!modified_substring.isEmpty())
        {
            
            // first replace the two-byte nukta_varNa with corresponding one-byte nukta varNas.

            modified_substring = modified_substring.replace( "क़" , "क़" )  ;
            modified_substring = modified_substring.replace( "ख़‌" , "ख़" )  ;
            modified_substring = modified_substring.replace( "ग़" , "ग़" )  ;
            modified_substring = modified_substring.replace( "ज़" , "ज़" )  ;
            modified_substring = modified_substring.replace( "ड़" , "ड़" )  ;
            modified_substring = modified_substring.replace( "ढ़" , "ढ़" )  ;
            modified_substring = modified_substring.replace( "ऩ" , "ऩ" )  ;
            modified_substring = modified_substring.replace( "फ़" , "फ़" )  ;
            modified_substring = modified_substring.replace( "य़" , "य़" )  ;
            modified_substring = modified_substring.replace( "ऱ" , "ऱ" )  ;


            // code for replacing "ि" (chhotee ee kii maatraa) with "f"  and correcting its position too.

            int position_of_f = modified_substring.indexOf( "ि" )  ;
            while ( position_of_f != -1 )  //while-02
            {
                char character_left_to_f = modified_substring.charAt( position_of_f - 1 )  ;
                modified_substring = modified_substring.replace( character_left_to_f + "ि" ,  "f" + character_left_to_f )  ;

                position_of_f = position_of_f - 1  ;
                char charAtStr;
                try{
                    charAtStr = modified_substring.charAt( position_of_f - 1);
                }catch(Exception ex){
                    charAtStr = '\u0000';
                }

                while (( charAtStr  == '्' )  &  ( position_of_f != 0  ) )
                {
                    String string_to_be_replaced = modified_substring.charAt( position_of_f - 2 ) + "्"  ;
                    modified_substring = modified_substring.replace( string_to_be_replaced + "f", "f" + string_to_be_replaced ) ;
                    position_of_f = position_of_f - 2  ;
                    //charAtStr = modified_substring.charAt( position_of_f - 1);
                    try{
                        charAtStr = modified_substring.charAt( position_of_f - 1);
                    }catch(Exception ex){
                        charAtStr = '\u0000';
                    }
                }
                position_of_f = modified_substring.indexOf( "ि" , position_of_f + 1 ) ; // search for f ahead of the current position. ....search...

            } // end of while-02 loop
            //************************************************************
            //     modified_substring = modified_substring.replace( /fर्/g , "£"  )  ;
            //************************************************************
            // Eliminating "र्" and putting  Z  at proper position for this.

            String set_of_matras = "ािीुूृेैोौं:ँॅ";

            modified_substring += "  "    ;  // add two spaces after the string to avoid UNDEFINED char in the following code.

            int position_of_half_R = modified_substring.indexOf( "र्" ) ;
            while ( position_of_half_R > 0  )  // while-04
            {
                // "र्"  is two bytes long
                int probable_position_of_Z = position_of_half_R + 2   ;

                char character_right_to_probable_position_of_Z = modified_substring.charAt( probable_position_of_Z + 1 );

                // trying to find non-maatra position right to probable_position_of_Z .

                while ( set_of_matras.indexOf( character_right_to_probable_position_of_Z ) != -1 )
                {
                    probable_position_of_Z = probable_position_of_Z + 1 ;
                    character_right_to_probable_position_of_Z = modified_substring.charAt( probable_position_of_Z + 1 ) ;
                } // end of while-05

                String string_to_be_replaced = modified_substring.substring( position_of_half_R + 2  , ( probable_position_of_Z + 1));// - position_of_half_R - 1 ))  ;
                modified_substring = modified_substring.replace( "र्" + string_to_be_replaced  ,  string_to_be_replaced + "Z" ) ;
                position_of_half_R = modified_substring.indexOf( "र्" ) ;
            } // end of while-04
            
            
            modified_substring = modified_substring.substring( 0 , modified_substring.length() - 2 )  ;


            //substitute array_two elements in place of corresponding array_one elements

            for( int input_symbol_idx = 0; input_symbol_idx < array_one_length; input_symbol_idx++ )
            {
                int idx = 0  ;  // index of the symbol being searched for replacement
                while (idx != -1 ) //whie-00
                {
                    modified_substring = modified_substring.replace( array_one[ input_symbol_idx ] , array_two[input_symbol_idx] );
                    idx = modified_substring.indexOf( array_one[input_symbol_idx] );
                } // end of while-00 loop
            } // end of for loop
            while(modified_substring.contains("fZ")){
                int fzIndex = modified_substring.indexOf("fZ");
                String str = modified_substring.substring(fzIndex + 2, fzIndex + 3);
                modified_substring = modified_substring.replace("fZ" + str, "f" + str + "Z");
            }
        } // end of IF  statement  meant to  supress processing of  blank  string.
        return modified_substring;

    } // end of the function  Replace_Symbols( )


}
