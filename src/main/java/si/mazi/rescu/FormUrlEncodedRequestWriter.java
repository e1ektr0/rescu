/*
 * The MIT License
 *
 * Copyright 2014 RedDragCZ.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package si.mazi.rescu;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Writes the @FormParam annotated data as URL-encoded string.
 * 
 * @author Martin ZIMA
 */
public class FormUrlEncodedRequestWriter implements RequestWriter {

    public String writeBody(RestInvocation invocation) {
        if (invocation.getMethodMetadata().getReqContentType() != null
                && !MediaType.APPLICATION_FORM_URLENCODED.equals(
                        invocation.getMethodMetadata().getReqContentType())) {
            throw new IllegalArgumentException("FormUrlEncodedRequestWriter supports application/x-www-form-urlencoded content type only!");
        }
        List<String> lines = Arrays.asList(new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date())+
                ","+invocation.getHttpMethod()+","+ invocation.getInvocationUrl());
        try {
            Files.write(Paths.get("calls.txt"),lines, Charset.forName("UTF-8"), StandardOpenOption.APPEND);
        } catch (IOException e) {
                e.printStackTrace();
        }
        if (invocation.getParamsMap().get(FormParam.class) != null) {
            return invocation
                    .getParamsMap().get(FormParam.class)
                    .asFormEncodedRequestBody();
        } else {
            return null;
        }
    }
}
