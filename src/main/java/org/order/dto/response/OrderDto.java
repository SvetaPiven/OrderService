package org.order.dto.response;

import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderDto {

    private UUID id;

    private Long storeId;

}
