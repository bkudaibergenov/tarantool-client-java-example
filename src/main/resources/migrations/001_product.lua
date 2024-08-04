return {
    up = function()
        local utils = require('migrator.utils')

        local space_name = 'Product'

        local space = box.schema.space.create(space_name, { if_not_exists = true })
        space:format({
            {name = 'id', type = 'unsigned'},
            {name = 'name', type = 'string'},
            {name = 'description', type = 'string', is_nullable = true},
            {name = 'price', type = 'decimal'},
            {name = 'quantity', type = 'integer'},
            {name = 'created', type = 'datetime' },
            {name = 'bucket_id', type = 'unsigned' },
        })
        space:create_index("primary", {
            parts = { { field = "id", type = "unsigned" } },
            if_not_exists = true
        })
        space:create_index("name_description_idx", {
            parts = {
                { field = "name", type = "string" },
                { field = "description", type = "string", is_nullable = true }
            },
            unique = false,
            if_not_exists = true
        })
        space:create_index("bucket_id", {
            parts = { { field = "bucket_id", type = "unsigned" } },
            unique = false,
            if_not_exists = true
        })

        utils.register_sharding_key(space_name, { 'id' })

        return true
    end
}
