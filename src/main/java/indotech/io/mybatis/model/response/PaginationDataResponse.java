package indotech.io.mybatis.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PaginationDataResponse<T> {
    boolean status = true;
    String message = "Data fetched";
    int page;
    int limit;
    Integer total;
    List<T> data;
}
