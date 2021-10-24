package io.xmeta.admin.model;


public class EntityFieldFilter implements java.io.Serializable {

    private EntityFieldWhereInput every;
    private EntityFieldWhereInput some;
    private EntityFieldWhereInput none;

    public EntityFieldFilter() {
    }

    public EntityFieldFilter(EntityFieldWhereInput every, EntityFieldWhereInput some, EntityFieldWhereInput none) {
        this.every = every;
        this.some = some;
        this.none = none;
    }

    public EntityFieldWhereInput getEvery() {
        return every;
    }
    public void setEvery(EntityFieldWhereInput every) {
        this.every = every;
    }

    public EntityFieldWhereInput getSome() {
        return some;
    }
    public void setSome(EntityFieldWhereInput some) {
        this.some = some;
    }

    public EntityFieldWhereInput getNone() {
        return none;
    }
    public void setNone(EntityFieldWhereInput none) {
        this.none = none;
    }



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private EntityFieldWhereInput every;
        private EntityFieldWhereInput some;
        private EntityFieldWhereInput none;

        public Builder() {
        }

        public Builder setEvery(EntityFieldWhereInput every) {
            this.every = every;
            return this;
        }

        public Builder setSome(EntityFieldWhereInput some) {
            this.some = some;
            return this;
        }

        public Builder setNone(EntityFieldWhereInput none) {
            this.none = none;
            return this;
        }


        public EntityFieldFilter build() {
            return new EntityFieldFilter(every, some, none);
        }

    }
}
