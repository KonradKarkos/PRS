package dtos.rowmodels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DictionaryRowModelDto{

    private Long id;
    private String key;
    private String symbol;
    private String value;
    private String dictionaryTypeBK;
    private String description;

}