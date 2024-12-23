package org.apache.lucene.analysis.vi;

import org.apache.lucene.analysis.BaseTokenStreamTestCase;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class VietnameseAnalyzerTest extends BaseTokenStreamTestCase {
    @Test
    void analyzeVietnamese() throws IOException {
        VietnameseAnalyzer analyzer = new VietnameseAnalyzer();
        TokenStream tokenStream = analyzer.tokenStream("test", "Hà Nội mùa này vắng những cơn mưa");
        assertTokenStreamContents(tokenStream, new String[]{"ha noi", "mua", "vang", "con", "mua"});
    }

    @Test
    void analyzeVietnamese2() throws IOException {
        VietnameseAnalyzer analyzer = new VietnameseAnalyzer();
        TokenStream tokenStream = analyzer.tokenStream("test", "\ncho tam giác ABC");
        assertTokenStreamContents(tokenStream, new String[]{"tam giac", "abc"});
    }

    @Test
    void analyzeVietnamese3() throws IOException {
        VietnameseAnalyzer analyzer = new VietnameseAnalyzer();
        TokenStream tokenStream = analyzer.tokenStream("test", "Trong các đẳng\nthức sau\n\n");
        assertTokenStreamContents(tokenStream, new String[]{"trong", "dang thuc"});
    }

    @Test
    void tokenOffset() throws IOException {
        VietnameseAnalyzer analyzer = new VietnameseAnalyzer();

        TokenStream ts = analyzer.tokenStream("test", "Phụ tùng xe Mazda bán tải dưới 7 chỗ: ống dẫn gió tới két làm mát khí nạp- cao su lưu hóa, mới 100%, phục vụ BHBD. Ms:1D0013246A");
        CharTermAttribute term = ts.addAttribute(CharTermAttribute.class);
        OffsetAttribute offset = ts.getAttribute(OffsetAttribute.class);
        ts.reset();
        String[] expected = new String[]{"phu tung", "xe", "mazda", "ban", "tai", "7", "cho", "ong", "dan", "gio", "toi", "ket", "lam", "mat", "khi", "nap", "cao su", "luu hoa", "moi", "100%", "phuc vu", "bhbd", "ms", "1", "d0013246a"};
        int[] expectedOffset = new int[]{0, 9, 12, 18, 22, 31, 33, 38, 42, 46, 50, 54, 58, 62, 66, 70, 75, 82, 91, 95, 101, 109, 115, 118, 119};

        for (int i = 0; i < expected.length; i++) {
            assertTrue(ts.incrementToken());
            assertEquals(expected[i], term.toString());
            assertEquals(expectedOffset[i], offset.startOffset());
        }
        assertFalse(ts.incrementToken());
    }
}
