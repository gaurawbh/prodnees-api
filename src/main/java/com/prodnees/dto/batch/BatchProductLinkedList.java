package com.prodnees.dto.batch;

public class BatchProductLinkedList {
    private int id;
    private int productId;
    private String name;
    private String description;
    Stat state;

    class Stat {
        private int id;
        private String name;
        private String description;
        Stat prev;
        Stat next;

        public int getId() {
            return id;
        }

        public Stat setId(int id) {
            this.id = id;
            return this;
        }

        public String getName() {
            return name;
        }

        public Stat setName(String name) {
            this.name = name;
            return this;
        }

        public String getDescription() {
            return description;
        }

        public Stat setDescription(String description) {
            this.description = description;
            return this;
        }

        public Stat getPrev() {
            return prev;
        }

        public Stat setPrev(Stat prev) {
            this.prev = prev;
            return this;
        }

        public Stat getNext() {
            return next;
        }

        public Stat setNext(Stat next) {
            this.next = next;
            return this;
        }
    }

    public int getId() {
        return id;
    }

    public BatchProductLinkedList setId(int id) {
        this.id = id;
        return this;
    }

    public int getProductId() {
        return productId;
    }

    public BatchProductLinkedList setProductId(int productId) {
        this.productId = productId;
        return this;
    }

    public String getName() {
        return name;
    }

    public BatchProductLinkedList setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public BatchProductLinkedList setDescription(String description) {
        this.description = description;
        return this;
    }

    public Stat addState() {
        return state;
    }

    public BatchProductLinkedList setState(Stat state) {
        this.state = state;
        return this;
    }
}
