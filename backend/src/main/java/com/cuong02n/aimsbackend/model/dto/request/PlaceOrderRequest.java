package com.cuong02n.aimsbackend.model.dto.request;

import com.cuong02n.aimsbackend.constant.Regex;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.HashSet;

@Data
public class PlaceOrderRequest {
    HashSet<Long> productIds;
    @Pattern(regexp = Regex.REGEX_CHECK_ADDRESS, message = "- Only     letters     (a-z     and     A-Z) or     digits     (0-9)     or     slashes are    \n" +
            "allowed\n" +
            "- Maximum    of    100    characters\n" +
            "- Must    not    null")
    String address;
    @Pattern(regexp = Regex.REGEX_CHECK_PHONE, message = " - Only    numbers    (0-9)    are    allowed\n" +
            "- Must    have    10 digits,    start    with    0\n" +
            "- May     include     separtors     such     as     dots     (.),     hyphens     (-)     or    \n" +
            "slashes    (/),    but    only    one    type    of    separator can    be    used in    \n" +
            "each    phone    number,    interleaved    with    the    digits.")
    String phone;
    String province;
    String shippingInstruction;
}


