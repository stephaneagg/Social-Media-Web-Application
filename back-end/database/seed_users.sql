INSERT INTO users (
    created_at,
    username,
    display_name,
    profile_image_url,
    bio,
    email,
    password_hash,
    "role"
)
VALUES
    (
        '2026-04-02 17:37:53.379 -0700',
        'steph_dev',
        'Stephane',
        'back-end/uploads/users/d922977e-b883-417c-a914-2e16f48365e1.jpeg',
        'Software developer and coffee enthusiast.',
        'steph@example.com',
        '$2a$10$vQAWImd30rpxN2hULbAndOmy/XPTbmUsHqa/aLvHhlNOM7ZjQwh6K',
        'USER'
    ),
    (
        '2026-04-05 17:37:53.379 -0700',
        'alexcodes',
        'Alex Chen',
        'back-end/uploads/users/069177ff-893f-4189-aaa2-9432a7f4bab1.png',
        'Building cool stuff with code.',
        'alex@example.com',
        '$2b$12$examplehash2',
        'USER'
    ),
    (
        '2026-04-23 17:37:53.379 -0700',
        'mod_jordan',
        'Jordan',
        'back-end/uploads/users/9ae12e02-922f-44bc-ac0a-034019388e2a.jpg',
        'Keeping the community running smoothly.',
        'jordan@example.com',
        '$2b$12$examplehash3',
        'USER'
    ),
    (
        '2026-04-15 17:37:53.379 -0700',
        'maya_designs',
        'Maya',
        'back-end/uploads/users/dfcebb2b-b8fb-440e-8a77-453393d4402f.png',
        'Designer and photographer.',
        'maya@example.com',
        '$2b$12$examplehash4',
        'USER'
    ),
    (
        NOW(),
        'User',
        'User',
        'back-end/uploads/users/db33609d-0891-4b4b-a1ca-95ea8cfdcbce.png',
        'Generic User',
        'user@system.com',
        '$2a$10$vQAWImd30rpxN2hULbAndOmy/XPTbmUsHqa/aLvHhlNOM7ZjQwh6K',
        'USER'
    );