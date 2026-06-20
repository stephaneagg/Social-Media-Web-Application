INSERT INTO users (
    created_at,
    username,
    display_name,
    profile_image_url,
    cover_image_url,
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
        '/uploads/users/d922977e-b883-417c-a914-2e16f48365e1.jpeg',
        '/uploads/users/2a9579ae-3674-4a98-b1a6-7400a39c2cd8.jpg',
        'Software developer and coffee enthusiast.',
        'steph@example.com',
        '$2a$10$vQAWImd30rpxN2hULbAndOmy/XPTbmUsHqa/aLvHhlNOM7ZjQwh6K',
        'USER'
    ),
    (
        '2026-04-05 17:37:53.379 -0700',
        'alexcodes',
        'Alex Chen',
        '/uploads/users/069177ff-893f-4189-aaa2-9432a7f4bab1.png',
        '/uploads/users/8bf222b7-b244-4b72-a6b4-c51dde37b5ac.jpg',
        'Building cool stuff with code.',
        'alex@example.com',
        '$2b$12$examplehash2',
        'USER'
    ),
    (
        '2026-04-23 17:37:53.379 -0700',
        'mod_jordan',
        'Jordan',
        '/uploads/users/9ae12e02-922f-44bc-ac0a-034019388e2a.jpg',
        '/uploads/users/8e71bf1e-ed90-4d9c-91e8-1c4ddb65e02e.jpg',
        'Keeping the community running smoothly.',
        'jordan@example.com',
        '$2b$12$examplehash3',
        'USER'
    ),
    (
        '2026-04-15 17:37:53.379 -0700',
        'maya_designs',
        'Maya',
        '/uploads/users/dfcebb2b-b8fb-440e-8a77-453393d4402f.png',
        '/uploads/users/ae95f5e1-332a-4835-9dae-da54e5b934ba.jpg',
        'Designer and photographer.',
        'maya@example.com',
        '$2b$12$examplehash4',
        'USER'
    ),
    (
        NOW(),
        'User',
        'User',
        '/uploads/users/778f789e-173e-4341-9bb3-1e883abf9539.jpg',
        '/uploads/users/669a474b-04d1-4bea-82fd-22b3659de434.jpg',
        'Generic User',
        'user@system.com',
        '$2a$10$vQAWImd30rpxN2hULbAndOmy/XPTbmUsHqa/aLvHhlNOM7ZjQwh6K',
        'USER'
    );
