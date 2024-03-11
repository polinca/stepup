package vtb.stepup.task_1;

import lombok.Getter;

import java.util.HashMap;

@Getter
public class Snapshot {

        private final String customerName;
        private final HashMap<Currency, Long> currencyMap;

        public Snapshot(String customerName, HashMap<Currency, Long> currencyMap) {
            this.customerName = customerName;
            this.currencyMap = currencyMap;
        }


    }

