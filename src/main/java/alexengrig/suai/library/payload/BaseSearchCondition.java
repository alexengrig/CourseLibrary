package alexengrig.suai.library.payload;

import lombok.Data;

@Data
public class BaseSearchCondition {
    private int page = 0;
    private int size = 10;
}
