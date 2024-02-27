package vtb.stepup;

import lombok.Getter;

import java.util.HashMap;

@Getter
public class Snapshot {

        private final String customerName;
        private final HashMap<Currency, Long> currencyMap;

        public HashMap<Currency, Long> getCurrencyMap() {
            return new HashMap(this.currencyMap);
        }

        public Snapshot(String customerName, HashMap<Currency, Long> currencyMap) {
            this.customerName = customerName;
            this.currencyMap = currencyMap;
        }


    }

